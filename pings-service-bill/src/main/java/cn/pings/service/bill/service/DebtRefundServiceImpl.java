package cn.pings.service.bill.service;

import cn.pings.service.api.bill.entity.DeptRefund;
import cn.pings.service.api.bill.service.DeptRefundService;
import cn.pings.service.api.common.service.AbstractBaseService;
import cn.pings.service.bill.mapper.DeptRefundMapper;
import com.alibaba.dubbo.config.annotation.Service;
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
public class DeptRefundServiceImpl extends AbstractBaseService<DeptRefundMapper, DeptRefund> implements DeptRefundService {

}
