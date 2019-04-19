package cn.pings.service.api.sys.entity;


import cn.pings.sys.commons.entity.AbstractReactTreeEntity;

/**
 *********************************************************
 ** @desc  ： 部门
 ** @author  Pings
 ** @date    2019/1/30
 ** @version v1.0
 * *******************************************************
 */
public class Dept extends AbstractReactTreeEntity {

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

    @Override
    public String getValue() {
        return this.getId() + "";
    }

    @Override
    public String getTitle() {
        return this.getName();
    }
}
