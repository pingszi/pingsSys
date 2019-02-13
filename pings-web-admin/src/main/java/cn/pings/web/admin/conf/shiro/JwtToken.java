package cn.pings.web.admin.conf.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 *********************************************************
 ** @desc  ： JwtToken
 ** @author  Pings
 ** @date    2019/1/23
 ** @version v1.0
 * *******************************************************
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
