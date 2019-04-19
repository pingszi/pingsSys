package cn.pings.service.api.sys.entity;

import cn.pings.sys.commons.entity.AbstractBaseEntity;

/**
 *********************************************************
 ** @desc  ： 角色权限
 ** @author  Pings
 ** @date    2019/3/12
 ** @version v1.0
 * *******************************************************
 */
public class RoleRight extends AbstractBaseEntity {

    private Integer RoleId;
    private Integer RightId;

    public Integer getRoleId() {
        return RoleId;
    }

    public void setRoleId(Integer roleId) {
        RoleId = roleId;
    }

    public Integer getRightId() {
        return RightId;
    }

    public void setRightId(Integer rightId) {
        RightId = rightId;
    }
}
