package cn.pings.service.api.sys.service;

import cn.pings.service.api.sys.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *********************************************************
 ** @desc  ： 用户管理本地存根，使用guava cache堆缓存用户信息
 ** @author  Pings
 ** @date    2019/4/10
 ** @version v1.0
 * *******************************************************
 */
public class UserServiceStub implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceStub.class);
    private static final User NULL_USER = new User();

    private static UserService userService;
    private static final LoadingCache<String, User> userCache = CacheBuilder.newBuilder()
            .concurrencyLevel(8)    //**并发数8
            .expireAfterWrite(10, TimeUnit.MINUTES)  //**10分钟后过期
            .maximumSize(10000)    //**最大容量10000条
            .softValues()          //**软引用缓存
            .build(new CacheLoader<String, User>() {
                       //**获取用户信息，并缓存。如果用户为空，则返回空用户，并缓存，防止缓存穿透的问题
                       @Override
                       public User load(String userName) {
                           User user = userService.getByUserName(userName);
                           if(user == null) {
                               logger.warn("用户不存在：" + userName);
                               user = NULL_USER;
                           }
                           return user;
                       }
                   }
            );

    //**构造函数传入真正的远程代理对象
    public UserServiceStub(UserService userServiceProxy) {
        userService = userServiceProxy;
    }

    @Override
    public User getById(int id) {
        return userService.getById(id);
    }

    @Override
    public User getByUserName(String userName) {
        User user = userCache.getUnchecked(this.getKey(userName));
        return user.equals(NULL_USER) ? null : user;
    }

    @Override
    public IPage<User> findPage(IPage<User> page, User entity) {
        return userService.findPage(page, entity);
    }

    @Override
    public List<User> findAll() {
        return userService.findAll();
    }

    @Override
    public User save(User user) {
        //**删除缓存
        userCache.invalidate(this.getKey(user.getUserName()));

        user = userService.save(user);

        return user;
    }

    @Override
    public int deleteById(int id) {
        //**删除缓存
        this.delCache(id);

        return userService.deleteById(id);
    }

    @Override
    public int allotRole(int id, int[] roles, int currentUserId) {
        //**删除缓存
        this.delCache(id);

        return userService.allotRole(id, roles, currentUserId);
    }

    //**获取用户在缓存中的key
    private String getKey(String userName) {
        return userName;
    }

    //**根据用户id删除缓存
    private void delCache(int id) {
        User user = userService.getById(id);
        userCache.invalidate(user.getUserName());
    }

}
