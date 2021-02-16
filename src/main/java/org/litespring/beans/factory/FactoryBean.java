package org.litespring.beans.factory;

/**
 * @autor sheltersodom
 * @create 2021-02-16-16:29
 */
public interface FactoryBean<T> {
    T getObject() throws Exception;

    Class<?> getObjectType();
}
