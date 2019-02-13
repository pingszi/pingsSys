package cn.pings.service.sys.service;

import cn.pings.service.api.common.entity.AbstractTreeEntity;
import cn.pings.service.api.sys.entity.Dept;
import cn.pings.service.api.sys.service.DeptService;
import cn.pings.service.sys.mapper.DeptMapper;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
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
@Service(version = "${sys.service.version}", cache = "lru")
@Component
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> findTreeAll() {
        List<Dept> list = this.deptMapper.selectList(new QueryWrapper<>());
        return AbstractTreeEntity.toTreeList(list);
    }
}
