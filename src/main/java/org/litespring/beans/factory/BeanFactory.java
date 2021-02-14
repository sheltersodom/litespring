package org.litespring.beans.factory;

import org.litespring.beans.BeanDefinition;

/**
 * @autor sheltersodom
 * @create 2021-02-04-22:40
 */
public interface BeanFactory {
    Object getBean(String beanID);

    Class<?> getType(String name) throws NoSuchBeanDefinitionException;
}
