package cn.pings.service.bill.service;

import cn.pings.service.api.bill.entity.BasData;
import cn.pings.service.api.bill.service.BasDataService;
import cn.pings.service.bill.mapper.BasDataMapper;
import cn.pings.sys.commons.service.AbstractBaseService;
import org.apache.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 *********************************************************
 ** @desc  ： 基础数据管理
 ** @author  Pings
 ** @date    2019/3/20
 ** @version v1.0
 * *******************************************************
 */
@Service(version = "${bill.service.version}")
@Component
public class BasDataServiceImpl extends AbstractBaseService<BasDataMapper, BasData> implements BasDataService {

    @Override
    public BasData getByCode(String code){
        BasData basData = new BasData();
        basData.setCode(code);

        return this.baseMapper.selectOne(new QueryWrapper<>(basData));
    }

    @Override
    public List<BasData> findByType(String type) {
        BasData basData = new BasData();
        basData.setType(type);

        return this.baseMapper.selectList(new QueryWrapper<>(basData));
    }
}
