package cn.pings.service.sys.service;

import cn.pings.service.api.common.entity.AbstractTreeEntity;
import cn.pings.service.api.common.service.AbstractBaseService;
import cn.pings.service.api.sys.entity.Dept;
import cn.pings.service.api.sys.service.DeptService;
import cn.pings.service.sys.mapper.DeptMapper;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<Dept> findTreeAll(Dept dept) {
        List<Dept> rst = new ArrayList<>();
        List<Dept> list = this.baseMapper.selectList(new QueryWrapper<>());

        if(StringUtils.isNotBlank(dept.getCode()) || StringUtils.isNotBlank(dept.getName())) {
            list.stream()
                .filter(d -> {
                    boolean flag = false;

                    if(StringUtils.isNotBlank(dept.getCode())) {
                        if(!d.getCode().contains(dept.getCode())) return false;
                        else flag = true;
                    }
                    if(StringUtils.isNotBlank(dept.getName())) {
                        if(!d.getName().contains(dept.getName())) return false;
                        else flag = true;
                    }

                    return flag;
                })
                .findAny().ifPresent(d -> rst.addAll(AbstractTreeEntity.toTreeList(list, d.getParentId())));
        } else
            rst.addAll(AbstractTreeEntity.toTreeList(list));

        return rst;
    }

    @Override
    public Dept getByCode(String code){
        Dept dept = new Dept();
        dept.setCode(code);

        return this.baseMapper.selectOne(new QueryWrapper<>(dept));
    }
}
