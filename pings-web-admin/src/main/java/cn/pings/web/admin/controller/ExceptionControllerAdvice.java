package cn.pings.web.admin.controller;

import cn.pings.sys.commons.util.ApiResponse;
import cn.pings.web.admin.util.HttpUtil;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 *********************************************************
 ** @desc  ： 异常处理,返回统一响应格式
 ** @author  Pings
 ** @date    2019/1/23
 ** @version v1.0
 * *******************************************************
 */
@RestControllerAdvice
public class ExceptionControllerAdvice extends AbstractBaseController {

    /**shiro的异常*/
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ApiResponse shiroException(ShiroException e) {
        e.printStackTrace();
        return new ApiResponse(401, e.getMessage(), null);
    }

    /**其他异常*/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ApiResponse globalException(HttpServletRequest request, Throwable ex) {
        ex.printStackTrace();
        return new ApiResponse(HttpUtil.getStatus(request).value(), ex.getMessage(), null);
    }
}
