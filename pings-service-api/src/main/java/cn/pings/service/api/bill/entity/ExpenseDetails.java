package cn.pings.service.api.bill.entity;

import cn.pings.sys.commons.entity.AbstractBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *********************************************************
 ** @desc  ： 消费明细
 ** @author   Pings
 ** @date     2019/3/20
 ** @version  v1.0
 * *******************************************************
 */
public class ExpenseDetails extends AbstractBaseEntity {

    private String name;       //**消费名称
    private Double value;      //**消费数目
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expenseDate;  //**消费日期
    private Integer typeId;   //**消费类型
    private String description; //**描述

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
