package cn.pings.service.sys.mapper;

import cn.pings.service.api.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *********************************************************
 ** @desc  ： 用户管理
 ** @author   Pings
 ** @date     2019/1/8
 ** @version  v1.0
 * *******************************************************
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     *********************************************************
     ** @desc ： 分页查询
     ** @author Pings
     ** @date   2019/1/30
     ** @param  page     page
     ** @param  entity   page
     ** @return List
     * *******************************************************
     */
    List<User> selectPage(IPage<User> page, @Param("entity")User entity);

}
