package org.litespring.beans.factory.config;

import org.litespring.beans.BeansException;

/**
 * @autor sheltersodom
 * @create 2021-02-13-22:31
 */
public interface BeanPostProcessor {
    Object beforeInitialization(Object bean, String beanName) throws BeansException;

    Object afterInitialization(Object bean, String beanName) throws BeansException;
}
