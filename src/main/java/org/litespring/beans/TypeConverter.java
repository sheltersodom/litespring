package org.litespring.beans;

/**
 * @autor sheltersodom
 * @create 2021-02-07-21:23
 */
public interface TypeConverter {
    <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;
}
