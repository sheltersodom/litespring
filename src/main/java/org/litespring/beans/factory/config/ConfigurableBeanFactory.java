package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;

/**
 * @autor sheltersodom
 * @create 2021-02-05-23:11
 */
public interface ConfigurableBeanFactory extends BeanFactory {
    void setBeanClassLoader(ClassLoader beanClassLoader);

    ClassLoader getBeanClassLoader();
}
