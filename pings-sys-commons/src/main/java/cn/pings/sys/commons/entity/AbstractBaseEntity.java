package cn.pings.sys.commons.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 *********************************************************
 ** @desc  ： 基础实体类
 ** @author  Pings
 ** @date    2019/1/8
 ** @version v1.0
 * *******************************************************
 */
public abstract class AbstractBaseEntity implements Serializable {

    //**编号
    private Integer id;
    //**添加人
    private Integer addWho;
    //**添加时间
    private Date addTime;
    //**编辑人
    private Integer editWho;
    //**编辑时间
    private Date editTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAddWho() {
        return addWho;
    }

    public void setAddWho(Integer addWho) {
        this.addWho = addWho;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getEditWho() {
        return editWho;
    }

    public void setEditWho(Integer editWho) {
        this.editWho = editWho;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**设置添加/修改人*/
    public void editAddWhoOrEditWho(int currentUserId) {
        if(this.id != null) this.setEditWho(currentUserId);
        else this.setAddWho(currentUserId);
    }

    /**设置添加/修改人*/
    public void editAddWhoOrEditWho(AbstractBaseEntity entity) {
        if(this.id != null) this.setEditWho(entity.getId());
        else this.setAddWho(entity.getId());
    }
}
