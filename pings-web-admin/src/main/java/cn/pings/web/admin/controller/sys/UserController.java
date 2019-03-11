package cn.pings.web.admin.controller.sys;

import cn.pings.service.api.common.util.ApiResponse;
import cn.pings.service.api.common.util.ReactPage;
import cn.pings.service.api.sys.entity.User;
import cn.pings.web.admin.controller.AbstractBaseController;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.DigestUtils;
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
        return new ApiResponse(200, this.iUserService.getByUserName(this.getCurrentUserName()));
    }

    /**
     *********************************************************
     ** @desc ： 查询用户列表
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

    /**
     *********************************************************
     ** @desc ： 验证用户名称是否唯一
     ** @author Pings
     ** @date   2019/2/14
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="验证用户名称是否唯一", notes="验证用户名称是否唯一")
    @GetMapping(value = "/validateUserNameUnique/{userName}")
    public ApiResponse validateUserNameUnique(@PathVariable("userName") String userName){
        User user = this.iUserService.getByUserName(userName);
        return new ApiResponse(200, user == null);
    }

    /**
     *********************************************************
     ** @desc ： 保存用户
     ** @author Pings
     ** @date   2019/2/15
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="保存用户", notes="保存用户")
    @PostMapping(value = "/save")
    @RequiresPermissions("sys:user:save")
    public ApiResponse save(User entity){
        entity.editAddWhoOrEditWho(this.getCurrentUser());
        if(StringUtils.isNotBlank(entity.getPassword()))
            entity.setPassword(DigestUtils.md5DigestAsHex(entity.getPassword().getBytes()));

        User user = this.iUserService.save(entity);

        return new ApiResponse(200,"保存成功", user);
    }

    /**
     *********************************************************
     ** @desc ： 删除用户
     ** @author Pings
     ** @date   2019/2/20
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="删除用户", notes="删除用户")
    @DeleteMapping(value = "/deleteById/{id}")
    @RequiresPermissions("sys:user:delete")
    public ApiResponse deleteById(@PathVariable("id") int id){
        this.iUserService.deleteById(id);

        return new ApiResponse(200,"删除成功");
    }
}
