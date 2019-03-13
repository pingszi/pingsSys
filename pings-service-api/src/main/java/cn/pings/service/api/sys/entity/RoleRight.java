package cn.pings.service.api.sys.entity;

import cn.pings.service.api.common.entity.AbstractBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;

import java.util.List;

/**
 *********************************************************
 ** @desc  ： 用户角色
 ** @author  Pings
 ** @date    2019/3/12
 ** @version v1.0
 * *******************************************************
 */
public class UserRole extends AbstractBaseEntity {

    private Integer userId;
    private Integer RoleId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return RoleId;
    }

    public void setRoleId(Integer roleId) {
        RoleId = roleId;
    }
}
