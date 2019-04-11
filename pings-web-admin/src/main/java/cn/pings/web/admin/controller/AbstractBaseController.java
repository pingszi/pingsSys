package cn.pings.web.admin.controller;

import cn.pings.service.api.sys.entity.User;
import cn.pings.service.api.sys.service.UserService;
import cn.pings.web.admin.util.JwtUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *********************************************************
 ** @desc  ： 基础Controller
 ** @author  Pings
 ** @date    2019/1/22
 ** @version v1.0
 * *******************************************************
 */
public abstract class AbstractBaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Reference(version = "${sys.service.version}")
    protected UserService userService;

    /**获取当前用户名称*/
    protected String getCurrentUserName(){
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        return JwtUtil.getUserName(token);
    }

    /**获取当前用户*/
    protected User getCurrentUser() {
        return this.userService.getByUserName(this.getCurrentUserName());
    }
}
