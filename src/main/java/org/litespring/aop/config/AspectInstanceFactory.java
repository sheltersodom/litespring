package org.litespring.aop.config;

import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.BeanFactoryAware;
import org.litespring.utils.StringUtils;

/**
 * @autor sheltersodom
 * @create 2021-02-16-16:23
 */
public class AspectInstanceFactory implements BeanFactoryAware {
    private String aspectBeanName;
    private BeanFactory beanFactory;

    public void setAspectBeanName(String aspectBeanName) {
        this.aspectBeanName = aspectBeanName;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        if (!StringUtils.hasText(this.aspectBeanName)) {
            throw new IllegalArgumentException("'aspectBeanName' is required");
        }
    }

    public Object getAspectInstance() {
        return this.beanFactory.getBean(this.aspectBeanName);
    }
}
