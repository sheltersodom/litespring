package org.litespring.beans.factory;

import org.litespring.beans.BeansException;

/**
 * @autor sheltersodom
 * @create 2021-02-05-16:01
 */
public class BeanCreationException extends BeansException {
    public BeanCreationException(String msg) {
        super(msg);
    }

    public BeanCreationException(String msg, Throwable cause) {
        super(msg, cause);
    }


}
