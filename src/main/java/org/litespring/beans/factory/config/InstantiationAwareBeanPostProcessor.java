package org.litespring.beans.factory.config;

import org.litespring.beans.BeansException;

/**
 * @autor sheltersodom
 * @create 2021-02-13-22:33
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    Object beforeInstantiation(Object bean, String beanName) throws BeansException;

    boolean afterInstantiation(Object bean, String beanName) throws BeansException;

    void postProcessPropertyValues(Object bean, String beanName) throws BeansException;
}
