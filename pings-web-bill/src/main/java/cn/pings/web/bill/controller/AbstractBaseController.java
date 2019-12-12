package cn.pings.web.bill.controller;

import cn.pings.commons.util.jwt.JwtUtil;
import cn.pings.jwt.verifier.JwtVerifier;
import cn.pings.service.api.sys.entity.Role;
import cn.pings.service.api.sys.entity.User;
import cn.pings.service.api.sys.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static cn.pings.service.api.bill.common.Commons.BILL_ADMINS;
import static cn.pings.service.api.bill.common.Constants.RedisKeyPrefix;

/**
 *********************************************************
 ** @desc  ： 基础Controller
 ** @author  Pings
 ** @date    2019/1/22
 ** @version v1.0
 * *******************************************************
 */
public abstract class AbstractBaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Reference(version = "${sys.service.version}")
    protected UserService userService;

    /**获取当前用户名称*/
    protected String getCurrentUserName(){
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        return JwtUtil.getValue(token, JwtVerifier.USER_NAME).asString();
    }

    /**获取当前用户*/
    protected User getCurrentUser() {
        return this.userService.getByUserName(this.getCurrentUserName());
    }

    /**获取当前用户是否是账单系统的管理员*/
    protected boolean billAdmin() {
        String key = RedisKeyPrefix.BILL_ADMIN + this.getCurrentUserName();
        Boolean exists = this.redisTemplate.hasKey(key);

        if(Objects.equals(exists, true)) {
            return Objects.equals(this.redisTemplate.opsForValue().get(key), "true");
        } else {
            boolean rst = this.getCurrentUser().getRoles().stream().map(Role::getCode).anyMatch(BILL_ADMINS::contains);
            this.redisTemplate.opsForValue().set(key, String.valueOf(rst), 10, TimeUnit.MINUTES);
            return  rst;
        }
    }
}
