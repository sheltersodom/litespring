package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor sheltersodom
 * @create 2021-02-05-11:17
 */
public class GenericBeanDefinition implements BeanDefinition {
    private String id;
    private String beanClassName;
    private boolean singleton = true;
    private boolean prototype = false;
    private Class<?> beanCache;


    public String getID() {
        return id;
    }

    @Override
    public Class<?> getBeanCache(String beanClassName) {
        return beanCache;
    }

    @Override
    public void SetBeanCache(Class<?> beanCache) {
        this.beanCache = beanCache;
    }

    private String scope = SCOPE_DEFAULT;
    private List<PropertyValue> propertyValues = new ArrayList<>();
    private ConstructorArgument constructorArgument = new ConstructorArgument();

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    public String getBeanClassName() {
        return this.beanClassName;
    }

    @Override
    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }


    @Override
    public boolean isSingleton() {
        return singleton;
    }

    @Override
    public boolean isPrototype() {
        return prototype;
    }

    @Override
    public String getScope() {
        return scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    @Override
    public ConstructorArgument getConstructorArgument() {
        return this.constructorArgument;
    }
}
