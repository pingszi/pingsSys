package cn.pings.service.sys.service;

import cn.pings.service.api.sys.entity.Role;
import cn.pings.service.api.sys.entity.User;
import cn.pings.service.api.sys.entity.UserRole;
import cn.pings.service.api.sys.service.UserService;
import cn.pings.service.sys.mapper.RightMapper;
import cn.pings.service.sys.mapper.RoleMapper;
import cn.pings.service.sys.mapper.UserMapper;
import cn.pings.service.sys.mapper.UserRoleMapper;
import cn.pings.sys.commons.service.AbstractBaseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
@Service(version = "${sys.service.version}", stub = "cn.pings.service.api.sys.service.UserServiceStub")
@Component
public class UserServiceImpl extends AbstractBaseService<UserMapper, User> implements UserService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RightMapper rightMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public User getById(int id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    public User getByUserName(String userName) {
        //**查询用户
        User entity = new User();
        entity.setUserName(userName);
        entity = this.baseMapper.selectOne(new QueryWrapper<>(entity));
        if(entity == null) return null;

        //**查询角色
        entity.setRoles(this.roleMapper.findByUserId(entity.getId()));

        //**查询权限
        List<Role> roles = entity.getRoles().stream().peek(role -> {
            //**admin角色包含所有权限
            if(role.getCode().equals("admin"))
                role.setRights(this.rightMapper.selectList(new QueryWrapper<>()));
            else
                role.setRights(this.rightMapper.findByRoleId(role.getId()));
        }).collect(toList());
        entity.setRoles(roles);

        return entity;
    }

    @Override
    public IPage<User> findPage(IPage<User> page, User entity){
        return page.setRecords(this.baseMapper.selectPage(page, entity));
    }

    @Override
    @Transactional
    public int allotRole(int id, int[] roles, int currentUserId) {
        //**删除用户角色
        UserRole entity = new UserRole();
        entity.setUserId(id);
        this.userRoleMapper.delete(new QueryWrapper<>(entity));

        //**添加用户角色
        return Arrays.stream(roles).map(roleId -> {
            entity.setRoleId(roleId);
            entity.setAddWho(currentUserId);
            return this.userRoleMapper.insert(entity);
        }).sum();
    }
}
