package cn.pings.service.api.sys.entity;

import cn.pings.sys.commons.entity.AbstractBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;

import java.util.List;

/**
 *********************************************************
 ** @desc  ： 角色
 ** @author  Pings
 ** @date    2019/1/8
 ** @version v1.0
 * *******************************************************
 */
public class Role extends AbstractBaseEntity {

    private String code;
    private String name;
    private String description;
    @TableField(exist = false)
    private List<Right> rights;

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

    public List<Right> getRights() {
        return rights;
    }

    public void setRights(List<Right> rights) {
        this.rights = rights;
    }
}
