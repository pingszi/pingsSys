package cn.pings.service.api.bill.common;

/**
 *********************************************************
 ** @desc  ： 账单-常量
 ** @author  Pings
 ** @date    2019/12/10
 ** @version v1.0
 * *******************************************************
 */
public final class Constants {

    /**
     *********************************************************
     ** @desc  ： 账单-redis键的前缀
     ** @author  Pings
     ** @date    2019/12/10
     ** @version v1.0
     * *******************************************************
     */
    public enum RedisKeyPrefix {
        BILL_ADMIN("bill_admin_", "是否是帐单管理员");

        private String code;
        private String description;
        RedisKeyPrefix(String code, String description) {
            this.code = code;
            this.description = description;
        }

        @Override
        public String toString() {
            return code;
        }
    }
}
