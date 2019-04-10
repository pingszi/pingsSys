package cn.pings.service.api.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;

/**
 *********************************************************
 ** @desc  ： 获取spring上下文/javabean，需要在spring中注册
 ** @author   Pings
 ** @date     2019/3/29
 ** @version  v1.0
 * *******************************************************
 */
public final class SpringContextUtil implements ApplicationContextAware {
      
        //**Spring上下文
        private static ApplicationContext applicationContext;

        /**
         *********************************************************
         ** @desc ：设置上下文环境，spring启动时自动调用
         ** @author Pings
         ** @date   2019/3/29
         ** @param  applicationContext   Spring上下文环
         * *******************************************************
         */
        @Override
        public void setApplicationContext(@Nullable ApplicationContext applicationContext) {
            SpringContextUtil.applicationContext = applicationContext;  
        }

        /**
         *********************************************************
         ** @desc ： 获取上下文环境
         ** @author  Pings
         ** @date    2019/3/29
         ** @return  ApplicationContext
         * *******************************************************
         */
        public static ApplicationContext getApplicationContext() {  
            return applicationContext;  
        }

        /**
         *********************************************************
         ** @desc ： 获取bean的实例
         ** @author  Pings
         ** @date    2019/3/29
         ** @param   name   bean的名称
         ** @return  Object
         * *******************************************************
         */
        @SuppressWarnings("unchecked")
        public static <T> T getBean(String name) {
            return (T) applicationContext.getBean(name);
        }  
    }

