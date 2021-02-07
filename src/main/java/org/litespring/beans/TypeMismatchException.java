package org.litespring.beans;

/**
 * @autor sheltersodom
 * @create 2021-02-07-21:25
 */
public class TypeMismatchException extends BeansException {
    private transient Object value;
    private Class<?> requiredType;

    public TypeMismatchException(Object value, Class<?> requiredType) {
        super("Failed to convert value:" + value + "to type" + requiredType);
        this.value = value;
        this.requiredType = requiredType;
    }

    public Object getValue() {
        return value;
    }

    public Class<?> getRequiredType() {
        return requiredType;
    }
}
