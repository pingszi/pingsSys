package cn.pings.service.sys.service;

import cn.pings.service.api.common.service.AbstractBaseService;
import cn.pings.service.api.sys.entity.Role;
import cn.pings.service.api.sys.service.RoleService;
import cn.pings.service.sys.mapper.RoleMapper;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;

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

    @Override
    public Role getByCode(String code){
        Role role = new Role();
        role.setCode(code);

        return this.baseMapper.selectOne(new QueryWrapper<>(role));
    }
}
