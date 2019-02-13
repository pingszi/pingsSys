package cn.pings.service.sys.service;

import cn.pings.service.api.sys.entity.Role;
import cn.pings.service.api.sys.entity.User;
import cn.pings.service.api.sys.service.UserService;
import cn.pings.service.sys.mapper.RightMapper;
import cn.pings.service.sys.mapper.RoleMapper;
import cn.pings.service.sys.mapper.UserMapper;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 *********************************************************
 ** @desc  ： 用户管理
 ** @author  Pings
 ** @date    2019/1/14
 ** @version v1.0
 * *******************************************************
 */
@Service(version = "${sys.service.version}", cache = "lru")
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RightMapper rightMapper;

    @Override
    public User getById(int id) {
        return this.userMapper.selectById(id);
    }

    @Override
    public User getByUserName(String userName) {
        //**查询用户
        User entity = new User();
        entity.setUserName(userName);
        entity = this.userMapper.selectOne(new QueryWrapper<>(entity));
        if(entity == null) return null;

        //**查询角色
        entity.setRoles(this.roleMapper.findByUserId(entity.getId()));

        //**查询权限
        List<Role> roles = entity.getRoles().stream().peek(role -> role.setRights(this.rightMapper.findByRoleId(role.getId()))).collect(toList());
        entity.setRoles(roles);

        return entity;
    }

    @Override
    public IPage<User> findPage(IPage<User> page, User entity){
        return page.setRecords(this.userMapper.selectPage(page, entity));
    }

    @Override
    public User save(User user) {
        this.userMapper.insert(user);
        return user;
    }
}
