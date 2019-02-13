package cn.pings.service.sys.mapper;

import cn.pings.service.api.sys.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *********************************************************
 ** @desc  ： 用户角色管理
 ** @author   Pings
 ** @date     2019/1/8
 ** @version  v1.0
 * *******************************************************
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    /**
     *********************************************************
     ** @desc ： 根据用户编号查询用户角色
     ** @author Pings
     ** @date   2019/1/24
     ** @param  userId  用户编号
     ** @return List
     * *******************************************************
     */
    @Select("select r.* " +
            "  from sys_user_role ur " +
            " inner join sys_role r on ur.role_id = r.id " +
            " where ur.user_id = #{userId} ")
    List<Role> findByUserId(int userId);

}
