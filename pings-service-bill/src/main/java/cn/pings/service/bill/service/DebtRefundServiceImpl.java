package cn.pings.service.bill.service;

import cn.pings.service.api.bill.entity.DebtRefund;
import cn.pings.service.api.bill.service.DebtRefundService;
import cn.pings.service.bill.mapper.DebtRefundMapper;
import cn.pings.sys.commons.service.AbstractBaseService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 *********************************************************
 ** @desc  ： 还款单管理
 ** @author  Pings
 ** @date    2019/3/28
 ** @version v1.0
 * *******************************************************
 */
@Service(version = "${bill.service.version}")
@Component
public class DebtRefundServiceImpl extends AbstractBaseService<DebtRefundMapper, DebtRefund> implements DebtRefundService {

}
