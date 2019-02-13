package cn.pings.web.admin.controller.sys;

import cn.pings.service.api.common.util.ApiResponse;
import cn.pings.service.api.common.util.ReactPage;
import cn.pings.service.api.sys.entity.User;
import cn.pings.service.api.sys.service.UserService;
import cn.pings.web.admin.controller.AbstractBaseController;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *********************************************************
 ** @desc  ： 用户管理
 ** @author  Pings
 ** @date    2019/1/29
 ** @version v1.0
 * *******************************************************
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractBaseController {

    @Autowired
    private UserService iUserService;

    /**
     *********************************************************
     ** @desc ： 获取当前用户
     ** @author Pings
     ** @date   2019/1/29
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="获取当前用户", notes="获取当前用户信息")
    @GetMapping(value = "/currentUser")
    @RequiresAuthentication
    public ApiResponse currentUser(){
        return new ApiResponse(200, this.iUserService.getByUserName(this.getToken()));
    }

    /**
     *********************************************************
     ** @desc ： 获取当前用户
     ** @author Pings
     ** @date   2019/1/29
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="查询用户", notes="查询所有用户")
    @GetMapping(value = "/list")
    @RequiresPermissions("sys:user:list")
    public ApiResponse list(ReactPage<User> page, User entity){
        ReactPage<User> list = (ReactPage<User>)this.iUserService.findPage(page, entity);
        return new ApiResponse(200, list.toReactPageFormat());
    }
}
