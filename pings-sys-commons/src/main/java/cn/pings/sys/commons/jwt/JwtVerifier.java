package cn.pings.sys.commons.jwt;

import cn.pings.sys.commons.util.JwtUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

/**
 *********************************************************
 ** @desc  ： Jwt校验器
 ** @author  Pings
 ** @date    2019/5/17
 ** @version v1.0
 * *******************************************************
 */
public interface JwtVerifier {

    //**用户名称的key
    String USER_NAME = "userName";

    /**
     *********************************************************
     ** @desc ： 生成令牌
     ** @author Pings
     ** @date   2019/5/17
     ** @param  userName    用户名
     ** @return String
     * *******************************************************
     */
    String sign(String userName);

    /**
     *********************************************************
     ** @desc ： 校验token
     ** @author Pings
     ** @date   2019/5/17
     ** @param  token       令牌
     ** @return boolean
     * *******************************************************
     */
    default boolean verify(String token) {
        JWTVerifier verifier = JWT.require(this.generateAlgorithm(this.getUserName(token))).build();
        verifier.verify(token);
        return true;
    }

    /**
     *********************************************************
     ** @desc ： 使签名无效，默认的签名在有效期内无法失效
     ** @author Pings
     ** @date   2019/5/20
     ** @param  userName    用户名
     ** @return String
     * *******************************************************
     */
    default void invalidateSign(String userName){ }

    /**
     *********************************************************
     ** @desc ： 根据secret生成jwt算法
     ** @author Pings
     ** @date   2019/5/17
     ** @param  userName 用户名称
     ** @return Algorithm
     * *******************************************************
     */
    default Algorithm generateAlgorithm(String userName){
        return Algorithm.HMAC256(this.generateUniqueSecret(userName));
    }

    /**
     *********************************************************
     ** @desc ： 根据基础密钥和用户名称生成唯一的密钥
     ** @author Pings
     ** @date   2019/5/17
     ** @param  userName 用户名称
     ** @return String
     * *******************************************************
     */
    default String generateUniqueSecret(String userName){
        return userName + "pingssys";
    }

    /**
     *********************************************************
     ** @desc ：根据token获取用户名称
     ** @author Pings
     ** @date   2019/5/17
     ** @param  token  令牌
     ** @return String
     * *******************************************************
     */
    default String getUserName(String token) {
        return JwtUtil.getValue(token, USER_NAME).asString();
    }

}
