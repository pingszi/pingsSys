package cn.pings.web.bill.controller;

import cn.pings.commons.util.spring.HttpStatusUtil;
import cn.pings.sys.commons.exception.PingsSysException;
import cn.pings.sys.commons.util.ApiResponse;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
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

    /**本系统的异常*/
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    @ExceptionHandler(PingsSysException.class)
    public ApiResponse SysException(PingsSysException e) {
        e.printStackTrace();

        return ApiResponse.failure(e.getMessage());
    }

    /**shiro访问权限验证异常*/
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UnauthorizedException.class)
    public ApiResponse UnauthorizedException(UnauthorizedException e) {
        e.printStackTrace();

        return new ApiResponse(403, e.getMessage());
    }

    /**shiro的其它异常*/
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ApiResponse shiroException(ShiroException e) {
        e.printStackTrace();

        return new ApiResponse(401, e.getMessage());
    }

    /**其他异常*/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ApiResponse globalException(HttpServletRequest request, Throwable ex) {
        ex.printStackTrace();

        return new ApiResponse(HttpStatusUtil.getStatus(request).value(), ex.getMessage());
    }
}
