package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/**
 * @autor sheltersodom
 * @create 2021-02-11-16:22
 */
public interface BeanNameGenerator {

    String generateBeanName(BeanDefinition bd, BeanDefinitionRegistry registry);
}
