package cn.pings.web.admin.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
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
    private static final String USER_NAME = "userName";
    //**签发时间戳的key
    private static final String SING_TIME_MILLIS = "signTimeMillis";
    //**默认的过期时间5分钟
    private static final long DEFAULT_EXPIRE_TIME = 5;
    //**默认的jwt加密secret
    private static final String DEFAULT_SECRET = "pingssys";

    /**
     *********************************************************
     ** @desc ：生成访问令牌
     ** @author Pings
     ** @date   2019/1/23
     ** @param  secret         secret
     ** @param  userName       用户名
     ** @param  expiresTime    过期时间
     ** @param  signTimeMillis 签发时间戳
     ** @return String
     * *******************************************************
     */
    public static String sign(String secret, String userName, long expiresTime, long signTimeMillis) {
        long currentTimeMillis = System.currentTimeMillis();
        //**签发时间戳
        signTimeMillis = signTimeMillis > 0 ? signTimeMillis : currentTimeMillis;

        //**过期时间
        expiresTime = expiresTime > 0 ? expiresTime : DEFAULT_EXPIRE_TIME;
        expiresTime = expiresTime * 60 * 1000;

        Algorithm algorithm = Algorithm.HMAC256(getSecret(secret, userName));
        return JWT.create().withClaim(USER_NAME, userName)
                  .withClaim(SING_TIME_MILLIS, signTimeMillis)
                  .withExpiresAt(new Date(currentTimeMillis + expiresTime)).sign(algorithm);
    }

    /**
     *********************************************************
     ** @desc ： 校验token
     ** @author Pings
     ** @date   2019/1/23
     ** @param  token    令牌
     ** @param  secret   secret
     ** @return boolean
     * *******************************************************
     */
    public static boolean verify(String token, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(getSecret(secret, JwtUtil.getUserName(token)));
        JWTVerifier verifier = JWT.require(algorithm).build();
        verifier.verify(token);
        return true;
    }

    /**
     *********************************************************
     ** @desc ： 获取用户名称
     ** @author Pings
     ** @date   2019/1/23
     ** @param  token  令牌
     ** @return String
     * *******************************************************
     */
    public static String getUserName(String token) {
        Claim claim = decodeToken(token, jwt -> jwt.getClaim(USER_NAME));
        return claim == null ? null : claim.asString();
    }

    /**
     *********************************************************
     ** @desc ： 获取签发时间戳
     ** @author Pings
     ** @date   2019/3/21
     ** @param  token  令牌
     ** @return long
     * *******************************************************
     */
    public static long getSignTimeMillis(String token) {
        Claim claim = decodeToken(token, jwt -> jwt.getClaim(SING_TIME_MILLIS));
        return claim == null ? -1 : claim.asLong();
    }

    /**
     *********************************************************
     ** @desc ：把访问令牌存放到响应的头信息中
     ** @author Pings
     ** @date   2019/3/21
     ** @param  response  响应
     ** @param  token     令牌
     * *******************************************************
     */
    public static void setHttpServletResponse(HttpServletResponse response, String token) {
        response.setHeader("Authorization", token);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
    }

    /**
     *********************************************************
     ** @desc ： token解码
     ** @author Pings
     ** @date   2019/1/23
     ** @param  token  标记
     ** @return T
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

    //**获取jwt加密secret
    private static String getSecret(String secret, String userName){
        return userName + (StringUtils.isNotBlank(secret) ? secret : DEFAULT_SECRET);
    }
}
