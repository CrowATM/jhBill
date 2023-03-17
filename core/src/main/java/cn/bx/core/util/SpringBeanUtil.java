package cn.bx.core.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * 非spring组件中获取bean
 *
 * @Author zk
 * @Data 2022/3/7 16:08
 */
@Component
public class SpringBeanUtil implements BeanFactoryPostProcessor {

    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        beanFactory = configurableListableBeanFactory;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBeanByName(String name) throws BeansException {
        return (T) beanFactory.getBean(name);
    }

    public static <T> T getBeanByClass(Class<T> clazz) throws BeansException {
        return (T) beanFactory.getBean(clazz);
    }
}
