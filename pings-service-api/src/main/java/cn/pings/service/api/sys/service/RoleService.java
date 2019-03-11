package cn.pings.service.api.sys.service;

import cn.pings.service.api.common.service.BaseService;
import cn.pings.service.api.sys.entity.Role;

/**
 *********************************************************
 ** @desc  ： 角色管理
 ** @author  Pings
 ** @date    2019/3/8
 ** @version v1.0
 * *******************************************************
 */
public interface RoleService extends BaseService<Role> {

    /**
     *********************************************************
     ** @desc ： 根据编码获取角色
     ** @author Pings
     ** @date   2019/3/8
     ** @param  code  编码
     ** @return Role
     * *******************************************************
     */
    Role getByCode(String code);

}
