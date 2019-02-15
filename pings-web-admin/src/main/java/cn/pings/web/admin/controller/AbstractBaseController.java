package cn.pings.web.admin.controller;

import cn.pings.service.api.sys.entity.User;
import cn.pings.service.api.sys.service.UserService;
import cn.pings.web.admin.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected UserService iUserService;

    /**获取当前用户名称*/
    protected String getCurrentUserName(){
        String token = this.request.getHeader("Authorization");
        return JwtUtil.getUserName(token);
    }

    /**获取当前用户*/
    protected User getCurrentUser() {
        return this.iUserService.getByUserName(this.getCurrentUserName());
    }
}
