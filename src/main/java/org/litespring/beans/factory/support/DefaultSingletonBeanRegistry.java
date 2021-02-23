package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.ObjectFactory;
import org.litespring.beans.factory.config.SingletonBeanRegistry;
import org.litespring.utils.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @autor sheltersodom
 * @create 2021-02-06-16:52
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    //一级缓存
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(64);
    //三级缓存
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);
    //二级缓存
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);
    //初始化缓存
    private final Set<String> singletonsCurrentlyInCreation = new CopyOnWriteArraySet<>();
    //Aop初始化问题处理
    private final Set<String> aopEarlyInCreation = new CopyOnWriteArraySet<>();

    public Set<String> getAopEarlyInCreation() {
        return aopEarlyInCreation;
    }

    public void addSigletonCurrentlyInCreation(String beanName) {
        this.singletonsCurrentlyInCreation.add(beanName);
    }

    public Map<String, Object> getEarlySingletonObjects() {
        return earlySingletonObjects;
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject, BeanDefinition bd) {
        Assert.notNull(beanName, "beaName must not be null");
        Object oldObject = singletonObjects.get(beanName);
        if (oldObject != null) {
            throw new IllegalStateException("Could not register object [" + singletonObject +
                    "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
        }
        singletonFactories.remove(beanName);
        earlySingletonObjects.remove(beanName);
        singletonsCurrentlyInCreation.remove(beanName);
        aopEarlyInCreation.remove(beanName);
        singletonObjects.put(beanName, singletonObject);
    }

    @Override
    public Object getSingleton(String beanName) {
        Object singletonObject = singletonObjects.get(beanName);
        if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
            singletonObject = this.earlySingletonObjects.get(beanName);
            if (singletonObject == null) {
                ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
                if (singletonFactory != null) {
                    singletonObject = singletonFactory.getObject();
                    this.earlySingletonObjects.put(beanName, singletonObject);
                    this.singletonFactories.remove(beanName);
                }
            }
        }
        return singletonObject;
    }

    public void addSingleFactory(String beanName, ObjectFactory<?> singletonFactory) {
        Assert.notNull(singletonFactory, "Singleton factory must not be null");
        if (!this.singletonObjects.containsKey(beanName)) {
            this.singletonFactories.put(beanName, singletonFactory);
            this.earlySingletonObjects.remove(beanName);
        }
    }

    public boolean isSingletonCurrentlyInCreation(String beanName) {
        return this.singletonsCurrentlyInCreation.contains(beanName);
    }

}
