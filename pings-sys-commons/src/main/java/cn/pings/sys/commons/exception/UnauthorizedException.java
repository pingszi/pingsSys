package cn.pings.sys.commons.exception;

/**
 *********************************************************
 ** @desc  ： 未授权异常
 ** @author  Pings
 ** @date    2019/1/23
 ** @version v1.0
 * *******************************************************
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String msg) {
        super(msg);
    }
}
