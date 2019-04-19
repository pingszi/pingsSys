package cn.pings.web.admin.controller.sys;

import cn.pings.service.api.sys.entity.Dept;
import cn.pings.service.api.sys.service.DeptService;
import cn.pings.sys.commons.util.ApiResponse;
import cn.pings.web.admin.controller.AbstractBaseController;
import org.apache.dubbo.config.annotation.Reference;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *********************************************************
 ** @desc  ： 部门管理
 ** @author  Pings
 ** @date    2019/1/30
 ** @version v1.0
 * *******************************************************
 */
@RestController
@RequestMapping("/api/dept")
public class DeptController extends AbstractBaseController {

    @Reference(version = "${sys.service.version}")
    private DeptService deptService;

    /**
     *********************************************************
     ** @desc ： 查询所有部门
     ** @author Pings
     ** @date   2019/1/30
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="查询所有部门", notes="查询所有部门信息")
    @GetMapping(value = "/findAll")
    @RequiresAuthentication
    public ApiResponse findAll(){
        List<Dept> depts = this.deptService.findTreeAll();

        return new ApiResponse(200, depts);
    }

    /**
     *********************************************************
     ** @desc ： 验证部门编码是否唯一
     ** @author Pings
     ** @date   2019/2/22
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="验证部门编码是否唯一", notes="验证部门编码是否唯一")
    @GetMapping(value = "/validateCodeUnique/{code}")
    public ApiResponse validateCodeUnique(@PathVariable("code") String code){
        Dept dept = this.deptService.getByCode(code);
        return new ApiResponse(200, dept == null);
    }

    /**
     *********************************************************
     ** @desc ： 保存用户
     ** @author Pings
     ** @date   2019/2/15
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="保存部门", notes="保存部门")
    @PostMapping(value = "/save")
    @RequiresPermissions("sys:dept:save")
    public ApiResponse save(Dept entity){
        entity.editAddWhoOrEditWho(this.getCurrentUser());
        Dept dept = this.deptService.save(entity);

        return new ApiResponse(200,"保存成功", dept);
    }

    /**
     *********************************************************
     ** @desc ： 删除部门
     ** @author Pings
     ** @date   2019/2/20
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="删除部门", notes="删除部门")
    @DeleteMapping(value = "/deleteById/{id}")
    @RequiresPermissions("sys:dept:delete")
    public ApiResponse deleteById(@PathVariable("id") int id){
        this.deptService.deleteById(id);

        return new ApiResponse(200,"删除成功");
    }
}
