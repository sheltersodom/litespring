package org.litespring.beans;

/**
 * @autor sheltersodom
 * @create 2021-02-05-16:00
 */
public class BeansException extends RuntimeException {
    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
