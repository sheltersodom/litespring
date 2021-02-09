package org.litespring.beans.factory.support;

import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;

/**
 * @autor sheltersodom
 * @create 2021-02-06-23:42
 */
public class BeanDefinitionValueResolver {
    private final BeanFactory factory;

    public BeanDefinitionValueResolver(BeanFactory factory) {
        this.factory = factory;
    }

    public Object resolveValueIfNecessary(PropertyValue pv, Object value) {
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            String beanName = ref.getBeanName();
            Object bean = factory.getBean(beanName);
            if (pv != null) pv.setConvertedValue(bean);
            return bean;
        } else if (value instanceof TypedStringValue) {
            return ((TypedStringValue) value).getValue();
        } else {
            //TODO
            throw new RuntimeException("the value" + value + "has not implemented");
        }
    }

    public Object resolveValueIfNecessary(Object value) {
        return resolveValueIfNecessary(null, value);
    }
}
