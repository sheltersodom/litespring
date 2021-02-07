package org.litespring.beans.factory.support;

import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;

/**
 * @autor sheltersodom
 * @create 2021-02-06-23:42
 */
public class BeanDefinitionValueResolver {
    private final DefaultBeanFactory factory;

    public BeanDefinitionValueResolver(DefaultBeanFactory factory) {
        this.factory = factory;
    }

    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            String beanName = ref.getBeanName();
            Object bean = factory.getBean(beanName);
            return bean;
        } else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else {
            //TODO
            throw new RuntimeException("the value" + value + "has not implemented");
        }
    }
}
