package cn.pings.web.admin.util;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

/**
 *********************************************************
 ** @desc  ： HttpUtil
 ** @author  Pings
 ** @date    2019/1/23
 ** @version v1.0
 * *******************************************************
 */
public class HttpUtil {

    /**
     *********************************************************
     ** @desc ： 获取http状态码
     ** @author Pings
     ** @date   2019/1/23
     ** @param  request     HttpServletRequest请求
     ** @return HttpStatus
     * *******************************************************
     */
    public static HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        return statusCode == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.valueOf(statusCode);
    }
}
