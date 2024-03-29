package org.litespring.beans.factory.support;

import org.litespring.aop.config.MethodLocatingFactory;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.PropertyValue;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @autor sheltersodom
 * @create 2021-02-05-11:17
 */
public class GenericBeanDefinition implements BeanDefinition {
    private String id;
    private String beanClassName;
    private boolean singleton = true;
    private boolean prototype = false;
    private SoftReference<Class<?>> beanCache;
    private String scope = SCOPE_DEFAULT;
    private List<PropertyValue> propertyValues = new ArrayList<>();
    private ConstructorArgument constructorArgument = new ConstructorArgument();
    //表明Bean是否是我们自己合成的
    private boolean isSynthetic = false;

    public GenericBeanDefinition() {
    }

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    public GenericBeanDefinition(Class<?> clazz) {
        this.beanCache = new SoftReference<>(clazz);
        this.beanClassName = clazz.getName();
    }

    public String getID() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Class<?> resolve(ClassLoader classLoader) throws ClassNotFoundException {
        Class<?> beanClass = null;
        if (Objects.isNull(beanCache)) {
            beanClass = classLoader.loadClass(beanClassName);
            beanCache = new SoftReference<>(beanClass);
        }
        beanClass = beanCache.get();
        return beanClass;
    }

    /**
     * 约定作为resolve方法的后续执行者
     *
     * @return
     */
    @Override
    public Class<?> getBeanClass() {
        if (this.beanCache == null) {
            throw new IllegalStateException("Bean class name [" + this.getBeanClassName() + "] has not been resolved into an actual Class");
        }
        return this.beanCache.get();
    }

    @Override
    public boolean hasBeanClass() {
        return this.beanCache != null;
    }

    @Override
    public Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException {
        return resolve(classLoader);
    }

    public void setSynthetic(boolean synthetic) {
        isSynthetic = synthetic;
    }

    @Override
    public boolean isSynthetic() {
        return isSynthetic;
    }

    public String getBeanClassName() {
        return this.beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
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
