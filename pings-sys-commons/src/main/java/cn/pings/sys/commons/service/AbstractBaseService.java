package cn.pings.sys.commons.service;

import cn.pings.sys.commons.entity.AbstractBaseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
     ** @desc ： 查询所有数据
     ** @author Pings
     ** @date   2019/3/12
     ** @return List
     * *******************************************************
     */
    @Override
    public List<T> findAll(){
        return this.baseMapper.selectList(new QueryWrapper<>());
    }

    /**
     *********************************************************
     ** @desc ： 保存
     ** @author Pings
     ** @date   2019/2/20
     ** @param  entity
     ** @return Debt
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
