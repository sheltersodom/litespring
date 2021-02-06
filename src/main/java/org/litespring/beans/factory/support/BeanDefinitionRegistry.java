package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/**
 * @autor sheltersodom
 * @create 2021-02-05-16:43
 */
public interface BeanDefinitionRegistry {
    BeanDefinition getBeanDefinition(String beanID);

    void registerBeanDefinition(String id, BeanDefinition bd);
}
