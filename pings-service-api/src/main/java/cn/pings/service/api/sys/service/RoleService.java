package cn.pings.service.api.sys.service;

import cn.pings.sys.commons.service.BaseService;
import cn.pings.service.api.sys.entity.Role;

import java.util.List;

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

    /**
     *********************************************************
     ** @desc ： 根据用户id查询角色
     ** @author Pings
     ** @date   2019/3/12
     ** @param  userId  用户编码
     ** @return List
     * *******************************************************
     */
    List<Role> findByUserId(int userId);

    /**
     *********************************************************
     ** @desc ： 分配权限
     ** @author Pings
     ** @date   2019/3/12
     ** @param  id            角色编号
     ** @param  rights        权限编号数组
     ** @param  currentUserId 操作用户编号
     ** @return int
     * *******************************************************
     */
    int allotRight(int id, int[] rights, int currentUserId);

}
