package cn.pings.web.bill.service;

import cn.pings.service.api.sys.entity.User;
import cn.pings.service.api.sys.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *********************************************************
 ** @desc  ： 用户管理
 ** @author  Pings
 ** @date    2019/3/26
 ** @version v1.0
 * *******************************************************
 */
@Component
@CacheConfig(cacheNames="user")
public class IUserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Reference(version = "${sys.service.version}")
    private UserService userService;

    @Override
    public User getById(int id) {
        throw new RuntimeException("This service does not exist");
    }

    @Override
    @Cacheable
    public User getByUserName(String userName) {
        return this.userService.getByUserName(userName);
    }

    @Override
    public IPage<User> findPage(IPage<User> page, User entity) {
        throw new RuntimeException("This service does not exist");
    }

    @Override
    public List<User> findAll() {
        throw new RuntimeException("This service does not exist");
    }

    @Override
    public User save(User user) {
        throw new RuntimeException("This service does not exist");
    }

    @Override
    public int deleteById(int id) {
        throw new RuntimeException("This service does not exist");
    }

    @Override
    public int allotRole(int id, int[] roles, int currentUserId) {
        throw new RuntimeException("This service does not exist");
    }
}
