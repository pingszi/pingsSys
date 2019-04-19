package cn.pings.web.bill.controller;

import cn.pings.service.api.bill.entity.Debt;
import cn.pings.service.api.bill.service.DebtService;
import cn.pings.sys.commons.util.ApiResponse;
import cn.pings.sys.commons.util.ReactPage;
import org.apache.dubbo.config.annotation.Reference;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
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
@RequestMapping("/bill/debt")
public class DebtController extends AbstractBaseController {

    @Reference(version = "${bill.service.version}")
    private DebtService debtService;

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
    @RequiresPermissions("bill:debt:list")
    public ApiResponse list(ReactPage<Debt> page, Debt entity){
        ReactPage<Debt> list = (ReactPage<Debt>)this.debtService.findPage(page, entity);
        return new ApiResponse(200, list.toReactPageFormat());
    }

    /**
     *********************************************************
     ** @desc ： 查询没有还清的欠款单
     ** @author Pings
     ** @date   2019/3/28
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="查询没有还清的欠款单", notes="查询没有还清的欠款单")
    @GetMapping(value = "/findAllNotRefundDebt")
    @RequiresAuthentication
    public ApiResponse findAllNotRefundDebt(){
        return new ApiResponse(200, this.debtService.findAllNotRefundDebt());
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
    @RequiresPermissions("bill:debt:save")
    public ApiResponse save(Debt entity){
        entity.editAddWhoOrEditWho(this.getCurrentUser());
        entity = this.debtService.save(entity);

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
    @RequiresPermissions("bill:debt:delete")
    public ApiResponse deleteById(@PathVariable("id") int id){
        this.debtService.deleteById(id);

        return new ApiResponse(200,"删除成功");
    }
}
