package cn.pings.service.bill.service;

import cn.pings.service.api.bill.entity.Dept;
import cn.pings.service.api.bill.service.DeptService;
import cn.pings.service.api.common.service.AbstractBaseService;
import cn.pings.service.bill.mapper.DeptMapper;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 *********************************************************
 ** @desc  ： 欠款单管理
 ** @author  Pings
 ** @date    2019/3/28
 ** @version v1.0
 * *******************************************************
 */
@Service(version = "${bill.service.version}")
@Component
public class DeptServiceImpl extends AbstractBaseService<DeptMapper, Dept> implements DeptService {

}
