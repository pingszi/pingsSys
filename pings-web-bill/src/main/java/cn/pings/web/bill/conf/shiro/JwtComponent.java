package cn.pings.web.bill.conf.shiro;

import cn.pings.web.bill.util.JwtUtil;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 *********************************************************
 ** @desc  ： Jwt工作组件
 ** @author   Pings
 ** @date     2019/3/21
 ** @version  v1.0
 * *******************************************************
 */
@Component
public class JwtComponent {

    @Value("${sys.jwt.access-token.expire-time}")
    private long accessTokenExpireTime;
    @Value("${sys.jwt.refresh-token.expire-time}")
    private long refreshTokenExpireTime;
    @Value("${sys.jwt.secret}")
    private String secret;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    //**默认的刷新令牌过期时间60分钟
    private static final long DEFAULT_REFRESH_EXPIRE_TIME = 60;
    //**缓存中保存refreshToken key的前缀
    private static final String REFRESH_TOKEN_PREFIX = "refresh_token_";

    /**
     *********************************************************
     ** @desc ：生成token
     ** @author Pings
     ** @date   2019/3/21
     ** @param  userName   用户名
     ** @return String
     * *******************************************************
     */
    public String sign(String userName) {
        //**refreshToken为当前时间戳
        long refreshToken = System.currentTimeMillis();

        //**获取access token
        String accessToken = JwtUtil.sign(secret, userName, accessTokenExpireTime, refreshToken);

        //**保存refreshToken
        refreshTokenExpireTime = refreshTokenExpireTime > 0 ? refreshTokenExpireTime : DEFAULT_REFRESH_EXPIRE_TIME;
        this.redisTemplate.opsForValue().set(this.getKey(userName), refreshToken, refreshTokenExpireTime, TimeUnit.MINUTES);

        return accessToken;
    }

    /**
     *********************************************************
     ** @desc ： 校验token
     ** @author Pings
     ** @date   2019/3/21
     ** @param  token    令牌
     ** @return boolean
     * *******************************************************
     */
    public boolean verify(String token) {
        String key = this.getKey(JwtUtil.getUserName(token));

        //**刷新令牌不存在/过期
        Boolean hasKey = this.redisTemplate.hasKey(key);
        if(hasKey == null || !hasKey)
            throw new TokenExpiredException("The Token not existed or expired.");

        //**刷新令牌和访问令牌的时间戳不一致
        long refreshToken = (long)this.redisTemplate.opsForValue().get(key);
        if(refreshToken != JwtUtil.getSignTimeMillis(token)){
            throw new TokenExpiredException("The Token has expired.");
        }

        //**访问令牌校验
        return JwtUtil.verify(token, secret);
    }

    //**获取缓存中保存refreshToken的key
    public String getKey(String userName) {
        return REFRESH_TOKEN_PREFIX + userName;
    }
}
