package cn.pings.service.api.sys.service;

import cn.pings.sys.commons.service.BaseService;
import cn.pings.service.api.sys.entity.Dept;

import java.util.List;

/**
 *********************************************************
 ** @desc  ： 部门管理
 ** @author  Pings
 ** @date    2019/1/30
 ** @version v1.0
 * *******************************************************
 */
public interface DeptService extends BaseService<Dept> {

    /**
     *********************************************************
     ** @desc ： 查询所有部门的树形结构
     ** @author Pings
     ** @date   2019/1/30
     ** @return List
     * *******************************************************
     */
    List<Dept> findTreeAll();

    /**
     *********************************************************
     ** @desc ： 根据编码获取部门
     ** @author Pings
     ** @date   2019/1/30
     ** @param  code  部门编码
     ** @return Debt
     * *******************************************************
     */
    Dept getByCode(String code);

}
