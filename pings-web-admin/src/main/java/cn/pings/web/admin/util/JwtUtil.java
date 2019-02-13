package cn.pings.web.admin.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.function.Function;

/**
 *********************************************************
 ** @desc  ： JwtUtil
 ** @author  Pings
 ** @date    2019/1/23
 ** @version v1.0
 * *******************************************************
 */
public class JwtUtil {

    //**用户名称的key
    public static final String USER_NAME = "userName";
    //**默认的过期时间180分钟
    public static final long DEFAULT_EXPIRE_TIME = 180 * 60 * 1000;

    /**
     *********************************************************
     ** @desc ：生成token
     ** @author Pings
     ** @date   2019/1/23
     ** @param  username 用户名
     ** @param  password 密码
     ** @return String
     * *******************************************************
     */
    public static String sign(String username, String password) {
        return sign(username, password, DEFAULT_EXPIRE_TIME);
    }

    /**
     *********************************************************
     ** @desc ：生成token
     ** @author Pings
     ** @date   2019/1/23
     ** @param  username    用户名
     ** @param  password    密码
     ** @param  expiresTime 过期时间
     ** @return String
     * *******************************************************
     */
    public static String sign(String username, String password, long expiresTime) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(password);
            return JWT.create().withClaim(USER_NAME, username)
                    .withExpiresAt(new Date(System.currentTimeMillis() + expiresTime)).sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *********************************************************
     ** @desc ： 校验token
     ** @author Pings
     ** @date   2019/1/23
     ** @param  token    标记
     ** @param  username 用户名
     ** @param  password 密码
     ** @return String
     * *******************************************************
     */
    public static boolean verify(String token, String username, String password) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(password);
            JWTVerifier verifier = JWT.require(algorithm).withClaim(USER_NAME, username).build();
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     *********************************************************
     ** @desc ： 获取用户名称
     ** @author Pings
     ** @date   2019/1/23
     ** @param  token  标记
     ** @return String
     * *******************************************************
     */
    public static String getUserName(String token) {
        Claim claim = decodeToken(token, jwt -> jwt.getClaim(USER_NAME));
        return claim == null ? null : claim.asString();
    }

    /**
     *********************************************************
     ** @desc ： token解码
     ** @author Pings
     ** @date   2019/1/23
     ** @param  token  标记
     ** @return String
     * *******************************************************
     */
    private static <T> T decodeToken(String token, Function<DecodedJWT, T> func) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return func.apply(jwt);
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
