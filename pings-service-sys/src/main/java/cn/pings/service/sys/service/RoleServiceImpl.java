package cn.pings.service.sys.service;

import cn.pings.service.api.sys.entity.Role;
import cn.pings.service.api.sys.entity.RoleRight;
import cn.pings.service.api.sys.service.RoleService;
import cn.pings.service.sys.mapper.RoleMapper;
import cn.pings.service.sys.mapper.RoleRightMapper;
import cn.pings.sys.commons.service.AbstractBaseService;
import org.apache.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 *********************************************************
 ** @desc  ： 角色管理
 ** @author  Pings
 ** @date    2019/3/8
 ** @version v1.0
 * *******************************************************
 */
@Service(version = "${sys.service.version}")
@Component
public class RoleServiceImpl extends AbstractBaseService<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleRightMapper roleRightMapper;

    @Override
    public Role getByCode(String code){
        Role role = new Role();
        role.setCode(code);

        return this.baseMapper.selectOne(new QueryWrapper<>(role));
    }

    @Override
    public List<Role> findByUserId(int userId) {
        return this.baseMapper.findByUserId(userId);
    }

    @Override
    @Transactional
    public int allotRight(int id, int[] rights, int currentUserId) {
        //**删除角色权限
        RoleRight entity = new RoleRight();
        entity.setRoleId(id);
        this.roleRightMapper.delete(new QueryWrapper<>(entity));

        //**添加角色权限
        return Arrays.stream(rights).map(rightId -> {
            entity.setRightId(rightId);
            entity.setAddWho(currentUserId);
            return this.roleRightMapper.insert(entity);
        }).sum();
    }
}
