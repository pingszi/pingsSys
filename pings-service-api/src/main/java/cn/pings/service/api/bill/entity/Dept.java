package cn.pings.service.api.bill.entity;

import cn.pings.service.api.common.entity.AbstractBaseEntity;

/**
 *********************************************************
 ** @desc  ： 基础数据
 ** @author  Pings
 ** @date    2019/3/20
 ** @version v1.0
 * *******************************************************
 */
public class BasData extends AbstractBaseEntity {

    private String code;
    private String name;
    private Integer sort;
    private String desc;
    private String type;
    private String typeDesc;

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }
}
