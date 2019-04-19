package cn.pings.web.admin.controller.sys;

import cn.pings.service.api.sys.entity.Role;
import cn.pings.service.api.sys.service.RoleService;
import cn.pings.sys.commons.util.ApiResponse;
import cn.pings.sys.commons.util.ReactPage;
import cn.pings.web.admin.controller.AbstractBaseController;
import org.apache.dubbo.config.annotation.Reference;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

/**
 *********************************************************
 ** @desc  ： 角色管理
 ** @author  Pings
 ** @date    2019/3/8
 ** @version v1.0
 * *******************************************************
 */
@RestController
@RequestMapping("/api/role")
public class RoleController extends AbstractBaseController {

    @Reference(version = "${sys.service.version}")
    private RoleService roleService;

    /**
     *********************************************************
     ** @desc ： 查询角色列表
     ** @author Pings
     ** @date   2019/3/8
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="查询角色", notes="查询所有角色")
    @GetMapping(value = "/list")
    @RequiresPermissions("sys:role:list")
    public ApiResponse list(ReactPage<Role> page, Role entity){
        ReactPage<Role> list = (ReactPage<Role>)this.roleService.findPage(page, entity);
        return new ApiResponse(200, list.toReactPageFormat());
    }

    /**
     *********************************************************
     ** @desc ： 查询所有角色
     ** @author Pings
     ** @date   2019/3/12
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="查询所有角色", notes="查询所有角色")
    @GetMapping(value = "/findAll")
    @RequiresAuthentication
    public ApiResponse findAll(){
        return new ApiResponse(200, this.roleService.findAll());
    }

    /**
     *********************************************************
     ** @desc ： 根据用户id查询角色
     ** @author Pings
     ** @date   2019/3/12
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="根据用户id查询角色", notes="根据用户id查询角色")
    @GetMapping(value = "/findByUserId/{userId}")
    @RequiresAuthentication
    public ApiResponse findByUserId(@PathVariable("userId") int userId){
        return new ApiResponse(200, this.roleService.findByUserId(userId));
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
        Role role = this.roleService.getByCode(code);
        return new ApiResponse(200, role == null);
    }

    /**
     *********************************************************
     ** @desc ： 保存
     ** @author Pings
     ** @date   2019/3/8
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="保存角色", notes="保存角色")
    @PostMapping(value = "/save")
    @RequiresPermissions("sys:role:save")
    public ApiResponse save(Role entity){
        entity.editAddWhoOrEditWho(this.getCurrentUser());
        Role role = this.roleService.save(entity);

        return new ApiResponse(200,"保存成功", role);
    }

    /**
     *********************************************************
     ** @desc ： 删除
     ** @author Pings
     ** @date   2019/3/8
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="删除角色", notes="删除角色")
    @DeleteMapping(value = "/deleteById/{id}")
    @RequiresPermissions("sys:role:delete")
    public ApiResponse deleteById(@PathVariable("id") int id){
        this.roleService.deleteById(id);

        return new ApiResponse(200,"删除成功");
    }

    /**
     *********************************************************
     ** @desc ： 分配权限
     ** @author Pings
     ** @date   2019/3/12
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="分配权限", notes="分配权限")
    @PostMapping(value = "/allotRight")
    @RequiresPermissions("sys:role:allotRight")
    public ApiResponse allotRight(int id, int[] rights){
        this.roleService.allotRight(id, rights, this.getCurrentUser().getId());

        return new ApiResponse(200,"分配成功");
    }
}
