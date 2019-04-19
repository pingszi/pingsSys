package cn.pings.web.admin.controller.sys;

import cn.pings.service.api.sys.entity.Right;
import cn.pings.service.api.sys.service.RightService;
import cn.pings.sys.commons.util.ApiResponse;
import cn.pings.web.admin.controller.AbstractBaseController;
import org.apache.dubbo.config.annotation.Reference;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

/**
 *********************************************************
 ** @desc  ： 权限管理
 ** @author  Pings
 ** @date    2019/3/8
 ** @version v1.0
 * *******************************************************
 */
@RestController
@RequestMapping("/api/right")
public class RightController extends AbstractBaseController {

    @Reference(version = "${sys.service.version}")
    private RightService rightService;

    /**
     *********************************************************
     ** @desc ： 查询所有权限
     ** @author Pings
     ** @date   2019/3/8
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="查询所有权限", notes="查询所有权限")
    @GetMapping(value = "/findAll")
    @RequiresAuthentication
    public ApiResponse findAll(){
        return new ApiResponse(200, this.rightService.findTreeAll());
    }

    /**
     *********************************************************
     ** @desc ： 根据角色id查询权限
     ** @author Pings
     ** @date   2019/3/13
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="根据角色id查询权限", notes="根据角色id查询权限")
    @GetMapping(value = "/findByRoleId/{roleId}")
    @RequiresAuthentication
    public ApiResponse findByRoleId(@PathVariable("roleId") int roleId){
        return new ApiResponse(200, this.rightService.findByRoleId(roleId));
    }

    /**
     *********************************************************
     ** @desc ： 验证编码是否唯一
     ** @author Pings
     ** @date   2019/3/8
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="验证编码是否唯一", notes="验证编码是否唯一")
    @GetMapping(value = "/validateCodeUnique/{code}")
    public ApiResponse validateCodeUnique(@PathVariable("code") String code){
        Right right = this.rightService.getByCode(code);
        return new ApiResponse(200, right == null);
    }

    /**
     *********************************************************
     ** @desc ： 保存
     ** @author Pings
     ** @date   2019/3/8
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="保存权限", notes="保存权限")
    @PostMapping(value = "/save")
    @RequiresPermissions("sys:right:save")
    public ApiResponse save(Right entity){
        entity.editAddWhoOrEditWho(this.getCurrentUser());
        Right right = this.rightService.save(entity);

        return new ApiResponse(200,"保存成功", right);
    }

    /**
     *********************************************************
     ** @desc ： 删除
     ** @author Pings
     ** @date   2019/3/8
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="删除权限", notes="删除权限")
    @DeleteMapping(value = "/deleteById/{id}")
    @RequiresPermissions("sys:right:delete")
    public ApiResponse deleteById(@PathVariable("id") int id){
        this.rightService.deleteById(id);

        return new ApiResponse(200,"删除成功");
    }
}
