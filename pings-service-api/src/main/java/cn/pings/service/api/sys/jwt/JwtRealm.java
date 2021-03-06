package cn.pings.service.api.sys.jwt;

import cn.pings.jwt.realm.AbstractJwtRealm;
import cn.pings.jwt.verifier.JwtVerifier;
import cn.pings.service.api.sys.entity.Right;
import cn.pings.service.api.sys.entity.Role;
import cn.pings.service.api.sys.entity.User;
import cn.pings.service.api.sys.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;
import java.util.Set;
import static java.util.stream.Collectors.toSet;
/**
 *********************************************************
 ** @desc  ： jwt realm
 ** @author  Pings
 ** @date    2019/5/10
 ** @version v1.0
 * *******************************************************
 */
public class JwtRealm extends AbstractJwtRealm {

    protected UserService userService;

    public JwtRealm(UserService userService, JwtVerifier verifier){
        super(verifier);
        this.userService = userService;
    }

    /**权限验证*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String userName = verifier.getUserName(principals.toString());

        //**获取用户
        User user = this.userService.getByUserName(userName);

        //**用户角色
        Set<String> roles = user.getRoles().stream().map(Role::getCode).collect(toSet());
        authorizationInfo.addRoles(roles);

        //**用户权限
        Set<String> rights = user.getRoles().stream().map(Role::getRights).flatMap(List::stream).map(Right::getCode).collect(toSet());
        authorizationInfo.addStringPermissions(rights);

        return authorizationInfo;
    }

    /**登录验证*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        //**获取用户名称
        String userName = verifier.getUserName(token);
        //**用户名称为空
        if (StringUtils.isBlank(userName)) {
            throw new UnknownAccountException("The account in Token is empty.");
        }

        //**获取用户
        User user = this.userService.getByUserName(userName);
        if (user == null) {
            throw new UnknownAccountException("The account does not exist.");
        }

        //**登录认证
        if (verifier.verify(token)) {
            return new SimpleAuthenticationInfo(token, token, "jwtRealm");
        }

        throw new AuthenticationException("Username or password error.");
    }

    /**管理员不验证权限*/
    @Override
    public  boolean isPermitted(PrincipalCollection principal, String permission){
        AuthorizationInfo info = this.getAuthorizationInfo(principal);
        return info.getRoles().contains("admin") || super.isPermitted(principal, permission);
    }

    /**管理员不验证角色*/
    @Override
    public boolean hasRole(PrincipalCollection principal, String roleIdentifier) {
        AuthorizationInfo info = this.getAuthorizationInfo(principal);
        return info.getRoles().contains("admin") || super.hasRole(principal, roleIdentifier);
    }
}
