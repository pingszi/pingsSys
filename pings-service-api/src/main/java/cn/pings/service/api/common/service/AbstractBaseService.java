package cn.pings.service.api.common.service;

import cn.pings.service.api.common.entity.AbstractBaseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *********************************************************
 ** @desc  ： 基础服务
 ** @author  Pings
 ** @date    2019/2/20
 ** @version v1.0
 * *******************************************************
 */
public abstract class AbstractBaseService<M extends BaseMapper<T>, T extends AbstractBaseEntity> implements BaseService<T> {

    @Autowired
    protected M baseMapper;

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
    @Override
    public IPage<T> findPage(IPage<T> page, T entity){
        return this.baseMapper.selectPage(page, new QueryWrapper<>(entity).orderByDesc("id"));
    }

    /**
     *********************************************************
     ** @desc ： 保存
     ** @author Pings
     ** @date   2019/2/20
     ** @param  entity
     ** @return Dept
     * *******************************************************
     */
    @Override
    public T save(T entity){
        if(entity.getId() != null)
            this.baseMapper.updateById(entity);
        else
            this.baseMapper.insert(entity);
        return entity;
    }

    /**
     *********************************************************
     ** @desc ： 根据ID删除
     ** @author Pings
     ** @date   2019/2/20
     ** @param  id
     ** @return int
     * *******************************************************
     */
    @Override
    public int deleteById(int id){
        return this.baseMapper.deleteById(id);
    }
}
