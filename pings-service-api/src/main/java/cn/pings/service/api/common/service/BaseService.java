package cn.pings.service.api.common.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *********************************************************
 ** @desc  ： 基础服务
 ** @author  Pings
 ** @date    2019/2/20
 ** @version v1.0
 * *******************************************************
 */
public interface AbstractBaseService<T> {

    /**
     *********************************************************
     ** @desc ： 分页查询
     ** @author Pings
     ** @date   2019/2/20
     ** @param  page
     ** @param  entity
     ** @return IPage
     * *******************************************************
     */
    IPage<T> findPage(IPage<T> page, T entity);

    /**
     *********************************************************
     ** @desc ： 保存
     ** @author Pings
     ** @date   2019/2/20
     ** @param  entity
     ** @return Dept
     * *******************************************************
     */
    T save(T entity);

    /**
     *********************************************************
     ** @desc ： 根据ID删除
     ** @author Pings
     ** @date   2019/2/20
     ** @param  id
     ** @return int
     * *******************************************************
     */
    int deleteById(int id);
}
