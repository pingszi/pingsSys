package cn.pings.service.api.bill.entity;

import cn.pings.sys.commons.entity.AbstractBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *********************************************************
 ** @desc  ： 欠款单
 ** @author   Pings
 ** @date     2019/3/20
 ** @version  v1.0
 * *******************************************************
 */
public class Debt extends AbstractBaseEntity {

    private String name;       //**欠款名称
    private Double value;      //**欠款数目
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date refundDate;   //**还款日期
    private Integer statusId;  //**状态

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

    public Date getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(Date refundDate) {
        this.refundDate = refundDate;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }
}
