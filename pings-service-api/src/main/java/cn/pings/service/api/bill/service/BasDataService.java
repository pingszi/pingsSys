package cn.pings.service.api.bill.service;

import cn.pings.service.api.bill.entity.BasData;
import cn.pings.sys.commons.service.BaseService;

import java.util.List;

/**
 *********************************************************
 ** @desc  ： 基础数据管理
 ** @author  Pings
 ** @date    2019/3/20
 ** @version v1.0
 * *******************************************************
 */
public interface BasDataService extends BaseService<BasData> {

    /**
     *********************************************************
     ** @desc ： 根据编码获取基础数据
     ** @author Pings
     ** @date   2019/3/20
     ** @param  code  编码
     ** @return Role
     * *******************************************************
     */
    BasData getByCode(String code);

    /**
     *********************************************************
     ** @desc ： 根据类型查询基础数据
     ** @author Pings
     ** @date   2019/3/20
     ** @param  type  类型
     ** @return List
     * *******************************************************
     */
    List<BasData> findByType(String type);

}
