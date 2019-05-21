package cn.pings.sys.commons.jwt;

import org.apache.shiro.ShiroException;

/**
 *********************************************************
 ** @desc  ： access token过期，refresh token未过期
 ** @author  Pings
 ** @date    2019/5/21
 ** @version v1.0
 * *******************************************************
 */
public class AccessTokenExpiredException extends ShiroException {

    public AccessTokenExpiredException(String message) {
        super(message);
    }
}
