package cn.pings.sys.commons.jwt;

import org.apache.shiro.ShiroException;

/**
 *********************************************************
 ** @desc  ： refresh token未过期
 ** @author  Pings
 ** @date    2019/5/21
 ** @version v1.0
 * *******************************************************
 */
public class RefreshTokenExpiredException extends ShiroException {

    public RefreshTokenExpiredException(String message) {
        super(message);
    }
}
