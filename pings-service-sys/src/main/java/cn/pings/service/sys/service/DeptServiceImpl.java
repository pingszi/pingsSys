package cn.pings.service.sys.service;

import cn.pings.service.api.sys.entity.Dept;
import cn.pings.service.api.sys.service.DeptService;
import cn.pings.service.sys.mapper.DeptMapper;
import cn.pings.sys.commons.entity.AbstractTreeEntity;
import cn.pings.sys.commons.service.AbstractBaseService;
import org.apache.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *********************************************************
 ** @desc  ： 部门管理
 ** @author  Pings
 ** @date    2019/1/14
 ** @version v1.0
 * *******************************************************
 */
@Service(version = "${sys.service.version}")
@Component
public class DeptServiceImpl extends AbstractBaseService<DeptMapper, Dept> implements DeptService {

    @Override
    public List<Dept> findTreeAll() {
        List<Dept> list = this.baseMapper.selectList(new QueryWrapper<>());
        return AbstractTreeEntity.toTreeList(list);
    }

    @Override
    public Dept getByCode(String code){
        Dept dept = new Dept();
        dept.setCode(code);

        return this.baseMapper.selectOne(new QueryWrapper<>(dept));
    }
}
