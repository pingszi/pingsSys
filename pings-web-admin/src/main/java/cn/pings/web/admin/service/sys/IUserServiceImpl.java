package cn.pings.web.admin.service.sys;

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
 ** @date    2019/1/25
 ** @version v1.0
 * *******************************************************
 */
@Component
@CacheConfig(cacheNames="user")
public class IUserServiceImpl implements UserService {

    public static final String USER_KEY_PREFIX = "user::";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Reference(version = "${sys.service.version}")
    private UserService userService;

    @Override
    public User getById(int id) {
        return this.userService.getById(id);
    }

    @Override
    @Cacheable
    public User getByUserName(String userName) {
        return this.userService.getByUserName(userName);
    }

    @Override
    public IPage<User> findPage(IPage<User> page, User entity) {
        IPage<User> rst = this.userService.findPage(page, entity);
        return rst;
    }

    @Override
    public List<User> findAll() {
        return this.userService.findAll();
    }

    @Override
    public User save(User user) {
        user = this.userService.save(user);

        //**删除缓存
        this.redisTemplate.delete(USER_KEY_PREFIX + user.getUserName());

        return user;
    }

    @Override
    public int deleteById(int id) {
        //**删除缓存
        User user = this.userService.getById(id);
        this.redisTemplate.delete(USER_KEY_PREFIX + user.getUserName());

        return this.userService.deleteById(id);
    }

    @Override
    public int allotRole(int id, int[] roles, int currentUserId) {
        //**删除缓存
        User user = this.userService.getById(id);
        this.redisTemplate.delete(USER_KEY_PREFIX + user.getUserName());

        return this.userService.allotRole(id, roles, currentUserId);
    }
}
