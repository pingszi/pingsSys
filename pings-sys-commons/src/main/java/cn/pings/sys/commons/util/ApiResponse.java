package cn.pings.sys.commons.util;

import java.io.Serializable;

/**
 *********************************************************
 ** @desc  ： 接口统一响应格式
 ** @author  Pings
 ** @date    2019/1/23
 ** @version v1.0
 * *******************************************************
 */
public class ApiResponse implements Serializable {

    //**返回的状态码
    private int code;

    //**返回的信息
    private String msg;

    //**返回的数据
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ApiResponse(int code) {
        this(code, null, null);
    }

    public ApiResponse(int code, Object data) {
        this(code, null, data);
    }

    public ApiResponse(int code, String msg) {
        this(code, msg, null);
    }

    public ApiResponse(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ApiResponse setAndGet(int code) {
        return setAndGet(code, null, null);
    }

    public ApiResponse setAndGet(int code, String msg) {
        return setAndGet(code, msg, null);
    }

    public ApiResponse setAndGet(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        return this;
    }
}
