package cn.pings.service.bill.service;

import cn.pings.service.api.bill.entity.BasData;
import cn.pings.service.api.bill.entity.Debt;
import cn.pings.service.api.bill.service.BasDataService;
import cn.pings.service.api.bill.service.DebtService;
import cn.pings.service.bill.mapper.DebtMapper;
import cn.pings.sys.commons.service.AbstractBaseService;
import org.apache.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
public class DebtServiceImpl extends AbstractBaseService<DebtMapper, Debt> implements DebtService {

    @Autowired
    private BasDataService basDataService;

    @Override
    public List<Debt> findAllNotRefundDebt() {
        BasData a = this.basDataService.getByCode("DEBT_STATUS_A");
        BasData b = this.basDataService.getByCode("DEBT_STATUS_B");

        QueryWrapper<Debt> query = new QueryWrapper<Debt>().eq("status_id", a.getId()).or().eq("status_id", b.getId());
        return this.baseMapper.selectList(query);
    }
}
