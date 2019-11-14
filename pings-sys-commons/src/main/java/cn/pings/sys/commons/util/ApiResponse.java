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

    public ApiResponse() {
        this(-1, null, null);
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

    public static ApiResponse success(String msg){
        return new ApiResponse(200, msg);
    }

    public static ApiResponse success(Object data){
        return new ApiResponse(200, data);
    }

    public static ApiResponse success(String msg, Object data){
        return new ApiResponse(200, msg, data);
    }

    public static ApiResponse failure(String msg){
        return new ApiResponse(400, msg);
    }

    public static ApiResponse failure(Object data){
        return new ApiResponse(400, data);
    }

    public static ApiResponse failure(String msg, Object data){
        return new ApiResponse(400, msg, data);
    }

}
