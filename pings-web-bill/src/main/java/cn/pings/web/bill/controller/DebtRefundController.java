package cn.pings.web.bill.controller;

import cn.pings.service.api.bill.entity.DebtRefund;
import cn.pings.service.api.bill.service.DebtRefundService;
import cn.pings.sys.commons.util.ApiResponse;
import cn.pings.sys.commons.util.ReactPage;
import org.apache.dubbo.config.annotation.Reference;
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
@RequestMapping("/bill/debtRefund")
public class DebtRefundController extends AbstractBaseController {

    @Reference(version = "${bill.service.version}")
    private DebtRefundService debtRefundService;

    /**
     *********************************************************
     ** @desc ： 查询还款单列表
     ** @author Pings
     ** @date   2019/3/28
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="查询还款单列表", notes="查询还款单列表")
    @GetMapping(value = "/list")
    @RequiresPermissions("bill:debtRefund:list")
    public ApiResponse list(ReactPage<DebtRefund> page, DebtRefund entity){
        ReactPage<DebtRefund> list = (ReactPage<DebtRefund>)this.debtRefundService.findPage(page, entity);
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
    @ApiOperation(value="保存还款单", notes="保存还款单")
    @PostMapping(value = "/save")
    @RequiresPermissions("bill:debtRefund:save")
    public ApiResponse save(DebtRefund entity){
        entity.editAddWhoOrEditWho(this.getCurrentUser());
        entity = this.debtRefundService.save(entity);

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
    @ApiOperation(value="删除还款单", notes="删除还款单")
    @DeleteMapping(value = "/deleteById/{id}")
    @RequiresPermissions("bill:debtRefund:delete")
    public ApiResponse deleteById(@PathVariable("id") int id){
        this.debtRefundService.deleteById(id);

        return new ApiResponse(200,"删除成功");
    }
}
