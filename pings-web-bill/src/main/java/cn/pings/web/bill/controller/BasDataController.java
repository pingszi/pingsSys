package cn.pings.web.bill.controller;

import cn.pings.service.api.bill.entity.BasData;
import cn.pings.service.api.bill.service.BasDataService;
import cn.pings.sys.commons.util.ApiResponse;
import cn.pings.sys.commons.util.ReactPage;
import org.apache.dubbo.config.annotation.Reference;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

/**
 *********************************************************
 ** @desc  ： 基础数据管理
 ** @author  Pings
 ** @date    2019/3/26
 ** @version v1.0
 * *******************************************************
 */
@RestController
@RequestMapping("/bill/basData")
public class BasDataController extends AbstractBaseController {

    @Reference(version = "${bill.service.version}")
    private BasDataService basDataService;

    /**
     *********************************************************
     ** @desc ： 查询数据管理列表
     ** @author Pings
     ** @date   2019/3/26
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="查询数据管理列表", notes="查询数据管理列表")
    @GetMapping(value = "/list")
    @RequiresPermissions("bill:basData:list")
    public ApiResponse list(ReactPage<BasData> page, BasData entity){
        ReactPage<BasData> list = (ReactPage<BasData>)this.basDataService.findPage(page, entity);
        return new ApiResponse(200, list.toReactPageFormat());
    }

    /**
     *********************************************************
     ** @desc ： 根据类型查询基础数据
     ** @author Pings
     ** @date   2019/3/26
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="根据类型查询基础数据", notes="根据类型查询基础数据")
    @GetMapping(value = "/findByType/{type}")
    @RequiresAuthentication
    public ApiResponse findByType(@PathVariable("type") String type){
        return new ApiResponse(200, this.basDataService.findByType(type));
    }

    /**
     *********************************************************
     ** @desc ： 验证编码是否唯一
     ** @author Pings
     ** @date   2019/3/26
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="验证编码是否唯一", notes="验证编码是否唯一")
    @GetMapping(value = "/validateCodeUnique/{code}")
    public ApiResponse validateCodeUnique(@PathVariable("code") String code){
        BasData entity = this.basDataService.getByCode(code);
        return new ApiResponse(200, entity == null);
    }

    /**
     *********************************************************
     ** @desc ： 保存
     ** @author Pings
     ** @date   2019/3/26
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="保存基础数据", notes="保存基础数据")
    @PostMapping(value = "/save")
    @RequiresPermissions("bill:basData:save")
    public ApiResponse save(BasData entity){
        entity.editAddWhoOrEditWho(this.getCurrentUser());
        entity = this.basDataService.save(entity);

        return new ApiResponse(200,"保存成功", entity);
    }

    /**
     *********************************************************
     ** @desc ： 删除
     ** @author Pings
     ** @date   2019/3/8
     ** @return ApiResponse
     * *******************************************************
     */
    @ApiOperation(value="删除基础数据", notes="删除基础数据")
    @DeleteMapping(value = "/deleteById/{id}")
    @RequiresPermissions("bill:basData:delete")
    public ApiResponse deleteById(@PathVariable("id") int id){
        this.basDataService.deleteById(id);

        return new ApiResponse(200,"删除成功");
    }
}
