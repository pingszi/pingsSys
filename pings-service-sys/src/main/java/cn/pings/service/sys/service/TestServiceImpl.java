package cn.pings.service.sys.service;

import cn.pings.service.api.sys.entity.User;
import cn.pings.service.api.sys.service.TestService;
import cn.pings.service.sys.mapper.UserMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *********************************************************
 ** @desc  ： 测试服务
 ** @author  Pings
 ** @date    2019/1/8
 ** @version v1.0
 * *******************************************************
 */
@Service(version = "${sys.service.version}")
@Component
public class TestServiceImpl implements TestService {

    @Autowired
    private UserMapper userMapper;

    public User getById(int id) {
        return this.userMapper.selectById(id);
    }

    public int save(User user) {
        return this.userMapper.insert(user);
    }
}
