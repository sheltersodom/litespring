package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;

/**
 * @autor sheltersodom
 * @create 2021-02-13-16:45
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    Object resolveDependency(DependencyDescriptor descriptor);
}
