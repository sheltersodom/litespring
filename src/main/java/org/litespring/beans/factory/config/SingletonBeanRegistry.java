package org.litespring.beans.factory.config;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.support.DefaultBeanFactory;

/**
 * @autor sheltersodom
 * @create 2021-02-06-16:48
 */
public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object singletonObject, BeanDefinition bd);

    Object getSingleton(String beanName);
}
