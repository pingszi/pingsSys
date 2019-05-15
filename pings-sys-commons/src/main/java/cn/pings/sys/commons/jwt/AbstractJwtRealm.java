package cn.pings.sys.commons.jwt;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.AuthorizingRealm;

/**
 *********************************************************
 ** @desc  ： jwt realm
 ** @author  Pings
 ** @date    2019/1/23
 ** @version v1.0
 * *******************************************************
 */
public abstract class AbstractJwtRealm extends AuthorizingRealm {

    /**必须重写此方法，不然Shiro会报错*/
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
}
