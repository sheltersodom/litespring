package org.litespring.beans;

import java.util.List;

/**
 * @autor sheltersodom
 * @create 2021-02-04-22:43
 */
public interface BeanDefinition {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
    String SCOPE_DEFAULT = "";

    boolean isSingleton();

    boolean isPrototype();

    String getScope();

    void setScope(String scope);

    String getBeanClassName();

    List<PropertyValue> getPropertyValues();

    ConstructorArgument getConstructorArgument();

    String getID();

    Class<?> resolve(ClassLoader classLoader) throws ClassNotFoundException;

    Class<?> getBeanClass() throws IllegalStateException;

    boolean hasBeanClass();

    Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException;
}
