package cn.pings.service.sys.service;

import cn.pings.service.api.sys.entity.Right;
import cn.pings.service.api.sys.entity.Role;
import cn.pings.service.api.sys.service.RightService;
import cn.pings.service.sys.mapper.RightMapper;
import cn.pings.service.sys.mapper.RoleMapper;
import cn.pings.sys.commons.entity.AbstractTreeEntity;
import cn.pings.sys.commons.service.AbstractBaseService;
import org.apache.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *********************************************************
 ** @desc  ： 权限管理
 ** @author  Pings
 ** @date    2019/3/8
 ** @version v1.0
 * *******************************************************
 */
@Service(version = "${sys.service.version}")
@Component
public class RightServiceImpl extends AbstractBaseService<RightMapper, Right> implements RightService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Right> findTreeAll() {
        List<Right> list = this.baseMapper.selectList(new QueryWrapper<>());
        return AbstractTreeEntity.toTreeList(list);
    }

    @Override
    public Right getByCode(String code){
        Right right = new Right();
        right.setCode(code);

        return this.baseMapper.selectOne(new QueryWrapper<>(right));
    }

    @Override
    public List<Right> findByRoleId(int roleId) {
        Role role = this.roleMapper.selectById(roleId);
        //**超级管理员拥有全部权限
        return role.getCode().equals("admin") ? this.baseMapper.selectList(new QueryWrapper<>())
                : this.baseMapper.findByRoleId(roleId);
    }
}
