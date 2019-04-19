package cn.pings.sys.commons.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.HashMap;
import java.util.Map;

/**
 *********************************************************
 ** @desc  ： react ant分页数据
 ** @author  Pings
 ** @date    2019/1/30
 ** @version v1.0
 * *******************************************************
 */
public class ReactPage<T> extends Page<T> {

    public long getPageSize() {
        return this.getSize();
    }

    public ReactPage<T> setPageSize(long size) {
        this.setSize(size);
        return this;
    }

    /**
     *********************************************************
     ** @desc ： 转换成react ant分页数据
     ** @author Pings
     ** @date   2019/1/30
     ** @return Map
     * *******************************************************
     */
    public Map<String, Object> toReactPageFormat(){
        Map<String, Object> rst = new HashMap<>();
        Map<String, Object> pagination = new HashMap<>();

        rst.put("pagination", pagination);
        rst.put("list", this.getRecords());

        pagination.put("total", this.getTotal());
        pagination.put("pageSize", this.getPageSize());
        pagination.put("current", this.getCurrent());

        return rst;
    }
}
