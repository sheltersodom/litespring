package org.litespring.beans.factory;

import org.litespring.beans.BeansException;

/**
 * @autor sheltersodom
 * @create 2021-02-05-16:01
 */
public class BeanDefinitionStoreException extends BeansException {
    public BeanDefinitionStoreException(String msg) {
        super(msg);
    }
    public BeanDefinitionStoreException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
