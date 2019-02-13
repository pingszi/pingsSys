package cn.pings.service.api.sys.entity;

import cn.pings.service.api.common.entity.AbstractReactTreeEntity;

import java.util.HashMap;
import java.util.Map;

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
    public Map<String, Object> toReactTreeMap(){
        Map<String, Object> rst = new HashMap<>();
        rst.put("key", this.getId());
        rst.put("value", this.getId());
        rst.put("title", this.getName());
        rst.put("parentId", this.getParentId());

        return rst;
    }
}
