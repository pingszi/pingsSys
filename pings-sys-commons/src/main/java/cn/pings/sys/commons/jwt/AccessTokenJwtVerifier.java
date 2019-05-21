package cn.pings.sys.commons.jwt;

import com.auth0.jwt.JWT;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 *********************************************************
 ** @desc  ： 基于access token的Jwt校验器
 ** @author  Pings
 ** @date    2019/5/17
 ** @version v1.0
 * *******************************************************
 */
public class AccessTokenJwtVerifier extends AbstractJwtVerifier {

    //**访问令牌过期时间(分钟)，默认600分钟，基于access token的方式，过期时间设置比较长，防止重复登录
    protected long accessTokenExpireTime = 600;

    public AccessTokenJwtVerifier(){
    }

    public AccessTokenJwtVerifier(String secret, long accessTokenExpireTime){
        if(StringUtils.isBlank(secret) || accessTokenExpireTime <= 0)
            throw new IllegalArgumentException("secret and accessTokenExpireTime catnot be null");

        this.secret = secret;
        this.accessTokenExpireTime = accessTokenExpireTime;
    }

    @Override
    public String sign(String userName) {
        return JWT.create()
                .withClaim(USER_NAME, userName)
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpireTime * 60 * 1000))
                .sign(this.generateAlgorithm(userName));
    }
}
