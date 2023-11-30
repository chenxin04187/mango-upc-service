package com.tobehonor.mangoupc.common.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取Spring容器中的bean
 *
 * @author William Chan
 * @since 2022/8/6
 */
@Component
public class SpringContext<T> implements ApplicationContextAware {
    
    private static ApplicationContext context;
    
    /**
     * 获取bean
     *
     * @param clazz 类
     * @param <T>   类的类型
     * @return 获取到的Spring中的bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return context != null ? context.getBean(clazz) : null;
    }
    
    /**
     * 获取bean
     *
     * @param beanName spring 容器的bean名称
     * @param <T>      指定的类型
     * @return 获取到的Spring中的bean
     */
    public static <T> T getBean(String beanName) {
        return context != null ? (T) context.getBean(beanName) : null;
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
