package cn.pings.service.api.bill.entity;

import cn.pings.sys.commons.entity.AbstractBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *********************************************************
 ** @desc  ： 欠款偿还表
 ** @author   Pings
 ** @date     2019/3/20
 ** @version  v1.0
 * *******************************************************
 */
public class DebtRefund extends AbstractBaseEntity {

    private String name;       //**偿还名称
    private Double value;      //**偿还数目
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date refundDate;   //**偿还日期
    private Integer debtId;    //**欠款

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

    public Integer getDebtId() {
        return debtId;
    }

    public void setDebtId(Integer debtId) {
        this.debtId = debtId;
    }
}
