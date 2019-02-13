package cn.pings.service.sys.mapper;

import cn.pings.service.api.sys.entity.Right;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *********************************************************
 ** @desc  ： 用户权限管理
 ** @author   Pings
 ** @date     2019/1/24
 ** @version  v1.0
 * *******************************************************
 */
@Repository
public interface RightMapper extends BaseMapper<Right> {

    /**
     *********************************************************
     ** @desc ： 根据角色编号查询角色权限
     ** @author Pings
     ** @date   2019/1/24
     ** @param  roleId  角色编号
     ** @return List
     * *******************************************************
     */
    @Select("select r.* " +
            "  from sys_role_right rr " +
            " inner join sys_right r on rr.right_id = r.id " +
            " where rr.role_id = #{roleId}")
    List<Right> findByRoleId(int roleId);
}
