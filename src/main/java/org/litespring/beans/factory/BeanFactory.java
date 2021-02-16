package org.litespring.beans.factory;

import org.litespring.aop.Advice;
import org.litespring.beans.BeanDefinition;

import java.util.List;

/**
 * @autor sheltersodom
 * @create 2021-02-04-22:40
 */
public interface BeanFactory {
    Object getBean(String beanID);

    Class<?> getType(String name) throws NoSuchBeanDefinitionException;

    List<Object> getBeansByType(Class<?> type);
}
