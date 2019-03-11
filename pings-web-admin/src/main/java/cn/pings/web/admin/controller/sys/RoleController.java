package cn.pings.web.admin.controller.sys;

import cn.pings.service.api.common.util.ApiResponse;
import cn.pings.service.api.common.util.ReactPage;
import cn.pings.service.api.sys.entity.Role;
import cn.pings.service.api.sys.service.RoleService;
import cn.pings.web.admin.controller.AbstractBaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import io.swagger.annotations.ApiOperation;
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
}
