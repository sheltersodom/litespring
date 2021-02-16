package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.FactoryBean;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;

/**
 * @autor sheltersodom
 * @create 2021-02-06-23:42
 */
public class BeanDefinitionValueResolver {
    private final AbstractBeanFactory factory;

    public BeanDefinitionValueResolver(AbstractBeanFactory factory) {
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
        } else if (value instanceof BeanDefinition) {
            BeanDefinition bd = (BeanDefinition) value;
            String innerBeanName = "(inner bean)" + bd.getBeanClassName() + "#" +
                    Integer.toHexString(System.identityHashCode(bd));
            return resolveInnerBean(innerBeanName, bd);
        } else {
            //TODO
            return value;
        }
    }

    private Object resolveInnerBean(String innerBeanName, BeanDefinition innerBd) {
        Object innerBean = this.factory.createBean(innerBd);
        if (innerBean instanceof FactoryBean) {
            try {
                return ((FactoryBean) innerBean).getObject();
            } catch (Exception e) {
                throw new BeanCreationException(innerBeanName, "FactoryBean threw exception on object creation", e);
            }
        } else {
            return innerBean;
        }
    }

    public Object resolveValueIfNecessary(Object value) {
        return resolveValueIfNecessary(null, value);
    }
}
