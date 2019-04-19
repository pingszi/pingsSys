package cn.pings.service.api.sys.service;

import cn.pings.service.api.sys.entity.User;


/**
 *********************************************************
 ** @desc  ： 测试服务
 ** @author  Pings
 ** @date    2019/1/8
 ** @version v1.0
 * *******************************************************
 */
public interface TestService {

    /**
     *********************************************************
     ** @desc ： 根据id查询用户(使用mybatisplus自动的crud)
     ** @author Pings
     ** @date   2019/1/8
     ** @param  id
     ** @return User
     * *******************************************************
     */
    User getById(int id);

    /**
     *********************************************************
     ** @desc ： 保存用户
     ** @author Pings
     ** @date   2019/1/8
     ** @param  user
     ** @return int
     * *******************************************************
     */
    int save(User user);
}
