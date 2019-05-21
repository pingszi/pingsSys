package cn.pings.sys.commons.jwt;

import cn.pings.sys.commons.util.JwtUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *********************************************************
 ** @desc  ： refresh token和access token结合的Jwt校验器
 ** @author  Pings
 ** @date    2019/5/17
 ** @version v1.0
 * *******************************************************
 */
public class RefreshTokenJwtVerifier extends AbstractJwtVerifier {

    //**刷新信息过期时间(分钟)，默认60分钟
    protected long refreshTokenExpireTime = 60;
    //**redisTemplate，用于存储refresh toke，并实现多个系统之间的共享
    protected RedisTemplate<String, Object> redisTemplate;
    //**缓存中保存refreshToken key的前缀
    private static final String REFRESH_TOKEN_PREFIX = "refresh_token_";

    public RefreshTokenJwtVerifier(RedisTemplate<String, Object> redisTemplate) {
        if(redisTemplate == null)
            throw new IllegalArgumentException("redisTemplate catnot be null");

        this.redisTemplate = redisTemplate;
    }

    @Override
    public String sign(String userName) {
        //**refreshToken为当前时间戳
        long refreshToken = System.currentTimeMillis();

        //**获取access token
        String accessToken = JWT.create()
                .withClaim(USER_NAME, userName)
                .withClaim(REFRESH_TOKEN_PREFIX, refreshToken)
                .withExpiresAt(new Date(refreshToken + accessTokenExpireTime * 60 * 1000))
                .sign(this.generateAlgorithm(userName));

        //**保存refreshToken
        this.redisTemplate.opsForValue().set(this.getKey(userName), refreshToken, refreshTokenExpireTime, TimeUnit.MINUTES);

        return accessToken;
    }

    @Override
    public boolean verify(String token) {
        String key = this.getKey(this.getUserName(token));

        //**刷新令牌不存在/过期
        Boolean hasKey = this.redisTemplate.hasKey(key);
        if (hasKey == null || !hasKey)
            throw new RefreshTokenExpiredException("The refresh token not existed or expired.");

        //**刷新令牌和访问令牌的时间戳不一致
        long refreshToken = (long) this.redisTemplate.opsForValue().get(key);
        if (refreshToken != this.getRefreshToken(token)) {
            throw new RefreshTokenExpiredException("The refresh token has expired.");
        }

        //**访问令牌校验
        try {
            return super.verify(token);
        } catch (TokenExpiredException e){
            throw new AccessTokenExpiredException("The access token has expired.");
        }
    }

    /**刷新令牌：通过删除缓存中的刷新令牌使token无效*/
    @Override
    public void invalidateSign(String userName){
        //**删除refresh token
        this.redisTemplate.delete(this.getKey(userName));
    }

    //**获取缓存中保存refreshToken的key
    private String getKey(String userName) {
        return REFRESH_TOKEN_PREFIX + userName;
    }

    //**获取jwt中的refresh token
    private long getRefreshToken(String token) {
        return JwtUtil.getValue(token, REFRESH_TOKEN_PREFIX).asLong();
    }

    public static class Builder {

        private RefreshTokenJwtVerifier verifier;

        private Builder() {
        }

        public static Builder newBuilder(RedisTemplate<String, Object> redisTemplate) {
            Builder builder = new Builder();
            builder.verifier = new RefreshTokenJwtVerifier(redisTemplate);
            return builder;
        }

        public Builder accessTokenExpireTime(long accessTokenExpireTime) {
            verifier.accessTokenExpireTime = accessTokenExpireTime;
            return this;
        }

        public Builder refreshTokenExpireTime(long refreshTokenExpireTime) {
            verifier.refreshTokenExpireTime = refreshTokenExpireTime;
            return this;
        }

        public Builder secret(String secret) {
            verifier.secret = secret;
            return this;
        }

        public RefreshTokenJwtVerifier build() {
            return verifier;
        }
    }
}
