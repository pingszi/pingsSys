package cn.pings.web.bill.controller;

import cn.pings.service.api.bill.entity.ExpenseDetails;
import cn.pings.service.api.bill.service.ExpenseDetailsService;
import cn.pings.sys.commons.util.ApiResponse;
import cn.pings.sys.commons.util.ReactPage;
import org.apache.dubbo.config.annotation.Reference;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

/**
 *********************************************************
 ** @desc  ： 消费明细管理
 ** @author  Pings
 ** @date    2019/3/28
 ** @version v1.0
 * *******************************************************
 */
@RestController
@RequestMapping("/bill/expenseDetails")
public class ExpenseDetailsController extends AbstractBaseController {

    @Reference(version = "${bill.service.version}")
    private ExpenseDetailsService expenseDetailsService;

    /**
     *********************************************************
     ** @desc ： 查询消费明细列表
     ** @author Pings
     ** @date   2019/3/28
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="查询消费明细列表", notes="查询消费明细列表")
    @GetMapping(value = "/list")
    @RequiresPermissions("bill:expenseDetails:list")
    public ApiResponse list(ReactPage<ExpenseDetails> page, ExpenseDetails entity){
        ReactPage<ExpenseDetails> list = (ReactPage<ExpenseDetails>)this.expenseDetailsService.findPage(page, entity);
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
    @ApiOperation(value="保存消费明细", notes="保存消费明细")
    @PostMapping(value = "/save")
    @RequiresPermissions("bill:expenseDetails:save")
    public ApiResponse save(ExpenseDetails entity){
        entity.editAddWhoOrEditWho(this.getCurrentUser());
        entity = this.expenseDetailsService.save(entity);

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
    @ApiOperation(value="删除消费明细", notes="删除消费明细")
    @DeleteMapping(value = "/deleteById/{id}")
    @RequiresPermissions("bill:expenseDetails:delete")
    public ApiResponse deleteById(@PathVariable("id") int id){
        this.expenseDetailsService.deleteById(id);

        return new ApiResponse(200,"删除成功");
    }
}
