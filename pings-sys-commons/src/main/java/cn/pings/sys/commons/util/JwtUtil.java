package cn.pings.sys.commons.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletResponse;
import java.util.function.Function;

/**
 *********************************************************
 ** @desc  ： JwtUtil
 ** @author  Pings
 ** @date    2019/1/23
 ** @version v1.0
 * *******************************************************
 */
public final class JwtUtil {

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
    public static <T> T decodeToken(String token, Function<DecodedJWT, T> func) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return func.apply(jwt);
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     *********************************************************
     ** @desc ： token解码
     ** @author Pings
     ** @date   2019/5/17
     ** @param  token  标记
     ** @param  key    键
     ** @return Claim
     * *******************************************************
     */
    public static Claim getValue(String token, String key){
        return decodeToken(token, decodedJWT -> decodedJWT.getClaim(key));
    }
}
