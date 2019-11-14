package cn.pings.service.api.sys.exception;

import cn.pings.sys.commons.exception.PingsSysException;

/**
 *********************************************************
 ** @desc  ： sys的异常
 ** @author  Pings
 ** @date    2019/11/13
 ** @version v1.0
 * *******************************************************
 */
public class SysException extends PingsSysException {

    public SysException(String message){
        super(message);
    }
}
