package cn.pings.service.sys.service;

import cn.pings.service.api.common.entity.AbstractTreeEntity;
import cn.pings.service.api.common.service.AbstractBaseService;
import cn.pings.service.api.sys.entity.Right;
import cn.pings.service.api.sys.service.RightService;
import cn.pings.service.sys.mapper.RightMapper;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *********************************************************
 ** @desc  ： 权限管理
 ** @author  Pings
 ** @date    2019/3/8
 ** @version v1.0
 * *******************************************************
 */
@Service(version = "${sys.service.version}")
@Component
public class RightServiceImpl extends AbstractBaseService<RightMapper, Right> implements RightService {

    @Override
    public List<Right> findTreeAll() {
        List<Right> list = this.baseMapper.selectList(new QueryWrapper<>());
        return AbstractTreeEntity.toTreeList(list);
    }

    @Override
    public Right getByCode(String code){
        Right right = new Right();
        right.setCode(code);

        return this.baseMapper.selectOne(new QueryWrapper<>(right));
    }
}
