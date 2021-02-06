package org.litespring.beans.factory.config;

/**
 * @autor sheltersodom
 * @create 2021-02-06-16:48
 */
public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);
}
