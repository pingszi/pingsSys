package cn.pings.service.api.bill.exception;

import cn.pings.sys.commons.exception.PingsSysException;

/**
 *********************************************************
 ** @desc  ： bill的异常
 ** @author  Pings
 ** @date    2019/11/13
 ** @version v1.0
 * *******************************************************
 */
public class BillException extends PingsSysException {

    public BillException(String message){
        super(message);
    }
}
