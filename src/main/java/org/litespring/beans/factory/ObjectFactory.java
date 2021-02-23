package org.litespring.beans.factory;

import org.litespring.beans.BeansException;

/**
 * @autor sheltersodom
 * @create 2021-02-20-16:02
 */
@FunctionalInterface
public interface ObjectFactory<T> {
    T getObject() throws BeansException;
}
