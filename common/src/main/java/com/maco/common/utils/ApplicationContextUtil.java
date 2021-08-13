package com.maco.common.utils;

import org.springframework.context.ApplicationContext;

public class ApplicationContextUtil {
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
    public static void setApplicationContext(ApplicationContext applicationContext){
        ApplicationContextUtil.applicationContext = applicationContext;
    }
    public static Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }
    public static <T> T getBean(String name, Class<T> clazz){
        return applicationContext.getBean(name, clazz);
    }
    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }
}
