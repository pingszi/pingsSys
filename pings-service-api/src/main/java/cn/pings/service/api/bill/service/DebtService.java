package cn.pings.service.api.bill.service;

import cn.pings.service.api.bill.entity.Debt;
import cn.pings.sys.commons.service.BaseService;
import java.util.List;

/**
 *********************************************************
 ** @desc  ： 欠款单管理
 ** @author  Pings
 ** @date    2019/3/28
 ** @version v1.0
 * *******************************************************
 */
public interface DebtService extends BaseService<Debt> {

    /**
     *********************************************************
     ** @desc ： 查询没有还清的欠款单
     ** @author Pings
     ** @date   2019/3/28
     ** @return List
     * *******************************************************
     */
    List<Debt> findAllNotRefundDebt();

}
