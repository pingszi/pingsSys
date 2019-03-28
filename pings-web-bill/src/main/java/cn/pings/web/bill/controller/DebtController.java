package cn.pings.web.bill.controller;

import cn.pings.service.api.bill.entity.Dept;
import cn.pings.service.api.bill.service.DeptService;
import cn.pings.service.api.common.util.ApiResponse;
import cn.pings.service.api.common.util.ReactPage;
import com.alibaba.dubbo.config.annotation.Reference;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

/**
 *********************************************************
 ** @desc  ： 欠款单管理
 ** @author  Pings
 ** @date    2019/3/28
 ** @version v1.0
 * *******************************************************
 */
@RestController
@RequestMapping("/bill/dept")
public class DeptController extends AbstractBaseController {

    @Reference(version = "${bill.service.version}")
    private DeptService deptService;

    /**
     *********************************************************
     ** @desc ： 查询欠款单列表
     ** @author Pings
     ** @date   2019/3/28
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="查询欠款单列表", notes="查询欠款单列表")
    @GetMapping(value = "/list")
    @RequiresPermissions("bill:dept:list")
    public ApiResponse list(ReactPage<Dept> page, Dept entity){
        ReactPage<Dept> list = (ReactPage<Dept>)this.deptService.findPage(page, entity);
        return new ApiResponse(200, list.toReactPageFormat());
    }

    /**
     *********************************************************
     ** @desc ： 保存
     ** @author Pings
     ** @date   2019/3/28
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="保存欠款单", notes="保存欠款单")
    @PostMapping(value = "/save")
    @RequiresPermissions("bill:dept:save")
    public ApiResponse save(Dept entity){
        entity.editAddWhoOrEditWho(this.getCurrentUser());
        entity = this.deptService.save(entity);

        return new ApiResponse(200,"保存成功", entity);
    }

    /**
     *********************************************************
     ** @desc ： 删除
     ** @author Pings
     ** @date   2019/3/28
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="删除欠款单", notes="删除欠款单")
    @DeleteMapping(value = "/deleteById/{id}")
    @RequiresPermissions("bill:dept:delete")
    public ApiResponse deleteById(@PathVariable("id") int id){
        this.deptService.deleteById(id);

        return new ApiResponse(200,"删除成功");
    }
}
