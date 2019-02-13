package cn.pings.service.api.sys.entity;

import cn.pings.service.api.common.entity.AbstractBaseEntity;

/**
 *********************************************************
 ** @desc  ： 角色
 ** @author  Pings
 ** @date    2019/1/8
 ** @version v1.0
 * *******************************************************
 */
public class Right extends AbstractBaseEntity {

    private String code;
    private String name;
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
