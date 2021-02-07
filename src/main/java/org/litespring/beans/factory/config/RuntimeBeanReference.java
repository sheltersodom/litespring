package org.litespring.beans.factory.config;

/**
 * @autor sheltersodom
 * @create 2021-02-06-22:54
 */
public class RuntimeBeanReference {
    private final String beanName;

    public RuntimeBeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
