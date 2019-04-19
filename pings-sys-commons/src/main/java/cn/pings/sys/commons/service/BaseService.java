package cn.pings.sys.commons.service;

import cn.pings.sys.commons.entity.AbstractBaseEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 *********************************************************
 ** @desc  ： 基础服务
 ** @author  Pings
 ** @date    2019/2/20
 ** @version v1.0
 * *******************************************************
 */
public interface BaseService<T extends AbstractBaseEntity> {

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
     ** @desc ： 查询所有数据
     ** @author Pings
     ** @date   2019/3/12
     ** @return List
     * *******************************************************
     */
    List<T> findAll();

    /**
     *********************************************************
     ** @desc ： 保存
     ** @author Pings
     ** @date   2019/2/20
     ** @param  entity
     ** @return Debt
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
