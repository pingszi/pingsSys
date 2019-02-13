package cn.pings.web.admin.controller.sys;

import cn.pings.service.api.common.entity.AbstractReactTreeEntity;
import cn.pings.service.api.common.util.ApiResponse;
import cn.pings.service.api.sys.entity.Dept;
import cn.pings.service.api.sys.service.DeptService;
import cn.pings.web.admin.controller.AbstractBaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return new ApiResponse(200, AbstractReactTreeEntity.toReactTreeList(depts));
    }
}
