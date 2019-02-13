package cn.pings.service.api.sys.service;

import cn.pings.service.api.sys.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *********************************************************
 ** @desc  ： 用户管理
 ** @author  Pings
 ** @date    2019/1/14
 ** @version v1.0
 * *******************************************************
 */
public interface UserService {

    /**
     *********************************************************
     ** @desc ： 根据id查询用户
     ** @author Pings
     ** @date   2019/1/8
     ** @param  id   用户编号
     ** @return User
     * *******************************************************
     */
    User getById(int id);

    /**
     *********************************************************
     ** @desc ： 根据用户名称查询用户
     ** @author Pings
     ** @date   2019/1/14
     ** @param  userName  用户名称
     ** @return User
     * *******************************************************
     */
    User getByUserName(String userName);

    /**
     *********************************************************
     ** @desc ： 分页查询
     ** @author Pings
     ** @date   2019/1/14
     ** @param  page
     ** @param  entity
     ** @return IPage
     * *******************************************************
     */
    IPage<User> findPage(IPage<User> page, User entity);

    /**
     *********************************************************
     ** @desc ： 保存用户
     ** @author Pings
     ** @date   2019/1/8
     ** @param  user
     ** @return User
     * *******************************************************
     */
    User save(User user);
}
