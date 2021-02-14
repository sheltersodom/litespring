package org.litespring.beans.factory;

import org.litespring.beans.BeansException;
import org.litespring.utils.StringUtils;

/**
 * @autor sheltersodom
 * @create 2021-02-14-21:13
 */
public class NoSuchBeanDefinitionException extends BeansException {
    private String beanName;
    private Class<?> beanType;

    /**
     * Create a new {@code NoSuchBeanDefinitionException}.
     *
     * @param name the name of the missing bean
     */
    public NoSuchBeanDefinitionException(String name) {
        super("No bean named '" + name + "' is defined");
        this.beanName = name;
    }

    /**
     * Create a new {@code NoSuchBeanDefinitionException}.
     *
     * @param name    the name of the missing bean
     * @param message detailed message describing the problem
     */
    public NoSuchBeanDefinitionException(String name, String message) {
        super("No bean named '" + name + "' is defined: " + message);
        this.beanName = name;
    }

    /**
     * Create a new {@code NoSuchBeanDefinitionException}.
     *
     * @param type required type of the missing bean
     */
    public NoSuchBeanDefinitionException(Class<?> type) {
        super("No qualifying bean of type [" + type.getName() + "] is defined");
        this.beanType = type;
    }

    /**
     * Create a new {@code NoSuchBeanDefinitionException}.
     *
     * @param type    required type of the missing bean
     * @param message detailed message describing the problem
     */
    public NoSuchBeanDefinitionException(Class<?> type, String message) {
        super("No qualifying bean of type [" + type.getName() + "] is defined: " + message);
        this.beanType = type;
    }

    /**
     * Create a new {@code NoSuchBeanDefinitionException}.
     *
     * @param type                  required type of the missing bean
     * @param dependencyDescription a description of the originating dependency
     * @param message               detailed message describing the problem
     */
    public NoSuchBeanDefinitionException(Class<?> type, String dependencyDescription, String message) {
        super("No qualifying bean of type [" + type.getName() + "] found for dependency" +
                (StringUtils.hasLength(dependencyDescription) ? " [" + dependencyDescription + "]" : "") +
                ": " + message);
        this.beanType = type;
    }

    public String getBeanName() {
        return beanName;
    }

    public Class<?> getBeanType() {
        return beanType;
    }

    public int getNumberOfBeansFound() {
        return 0;
    }
}
