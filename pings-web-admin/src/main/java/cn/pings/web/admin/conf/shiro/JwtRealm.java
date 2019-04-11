package cn.pings.web.admin.conf.shiro;

import cn.pings.service.api.sys.entity.Right;
import cn.pings.service.api.sys.entity.Role;
import cn.pings.service.api.sys.entity.User;
import cn.pings.service.api.sys.service.UserService;
import cn.pings.web.admin.util.JwtUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import static java.util.stream.Collectors.toSet;

/**
 *********************************************************
 ** @desc  ： 自定义Realm
 ** @author  Pings
 ** @date    2019/1/23
 ** @version v1.0
 * *******************************************************
 */
public class JwtRealm extends AuthorizingRealm {

    @Reference(version = "${sys.service.version}")
    private UserService userService;
    @Autowired
    private JwtComponent jwtComponent;

    /**必须重写此方法，不然Shiro会报错*/
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**权限验证*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String userName = JwtUtil.getUserName(principals.toString());

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
        String userName = JwtUtil.getUserName(token);
        //**用户名称为空
        if (StringUtils.isBlank(userName)) {
            throw new AuthenticationException("The account in Token is empty.");
        }

        //**获取用户
        User user = this.userService.getByUserName(userName);
        if (user == null) {
            throw new AuthenticationException("The account does not exist.");
        }

        //**登录认证
        if (jwtComponent.verify(token)) {
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
