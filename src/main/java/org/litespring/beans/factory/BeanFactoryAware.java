package org.litespring.beans.factory;

import org.litespring.beans.BeansException;

/**
 * @autor sheltersodom
 * @create 2021-02-16-16:27
 */
public interface BeanFactoryAware {
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
