package org.litespring.beans.factory.support;

import org.apache.commons.beanutils.BeanUtils;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanFactoryAware;
import org.litespring.beans.factory.NoSuchBeanDefinitionException;
import org.litespring.beans.factory.config.BeanPostProcessor;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.litespring.utils.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @autor sheltersodom
 * @create 2021-02-04-22:42
 */
public class DefaultBeanFactory extends AbstractBeanFactory implements BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>(64);
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    private ClassLoader beanClassLoader;


    public BeanDefinition getBeanDefinition(String beanID) {
        return this.beanDefinitionMap.get(beanID);
    }

    @Override
    public void registerBeanDefinition(String id, BeanDefinition bd) {
        this.beanDefinitionMap.put(id, bd);
    }

    public Object getBean(String beanID) {
        BeanDefinition bd = this.getBeanDefinition(beanID);
        if (bd == null) {
            throw new BeanCreationException("Bean Definition does not exist");
        }
        if (bd.isSingleton()) {
            Object bean = this.getSingleton(beanID);
            if (bean == null) {
                bean = createBean(bd);
                this.registerSingleton(beanID, bean);
            }
            return bean;
        }
        return createBean(bd);
    }


    protected Object createBean(BeanDefinition bd) {
        //创建实例
//        Object bean = instantiateBean(bd);
        ConstructorResolver constructorResolver = new ConstructorResolver(this);
        Object bean = constructorResolver.autowireConstructor(bd);
        populateBean(bd, bean);
        bean = initializeBean(bd, bean);
        return bean;
    }

    protected void populateBean(BeanDefinition bd, Object bean) {
        for (BeanPostProcessor processor : this.getBeanPostProcessors()) {
            if (processor instanceof InstantiationAwareBeanPostProcessor) {
                ((InstantiationAwareBeanPostProcessor) processor).postProcessPropertyValues(bean, bd.getID());
            }
        }
        List<PropertyValue> pvs = bd.getPropertyValues();
        if (pvs == null || pvs.isEmpty()) {
            return;
        }
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
        SimpleTypeConverter converter = new SimpleTypeConverter();
        try {
            for (PropertyValue pv : pvs) {
                String propertyName = pv.getName();
                Object originalValue = pv.getValue();
                Object resolvedValue = valueResolver.resolveValueIfNecessary(pv, originalValue);
                //使用set方法进行属性注入
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor pd : pds) {
                    if (pd.getName().equals(propertyName)) {
                        Object convertedValue = converter.convertIfNecessary(resolvedValue, pd.getPropertyType());
                        pd.getWriteMethod().invoke(bean, convertedValue);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new BeanCreationException("Failed to obtain BeanInfo for class " + bd.getBeanClassName());
        }
    }

    private void populateBeanUseCommonUtils(BeanDefinition bd, Object bean) {
        List<PropertyValue> pvs = bd.getPropertyValues();
        if (pvs == null || pvs.isEmpty()) {
            return;
        }
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
        try {
            for (PropertyValue pv : pvs) {
                String propertyName = pv.getName();
                Object originalValue = pv.getValue();
                Object resolvedValue = valueResolver.resolveValueIfNecessary(pv, originalValue);
                BeanUtils.setProperty(bean, propertyName, resolvedValue);
            }
        } catch (Exception e) {
            throw new BeanCreationException("Populate bean property failed for " + bd.getBeanClassName());
        }
    }

    protected Object initializeBean(BeanDefinition bd, Object bean) {
        invokeAwareMethods(bean);
        //TODO 对Bean进行初始化
        if (!bd.isSynthetic()) {
            return applyBeanPostProcessorsAfterInitialization(bean, bd.getID());
        }
        return bean;
    }

    private Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            result = beanPostProcessor.afterInitialization(result, beanName);
            if (result == null) {
                return result;
            }
        }
        return result;
    }

    private void invokeAwareMethods(final Object bean) {
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
    }

//    private Object instantiateBean(BeanDefinition bd) {
//        ClassLoader c1 = this.getBeanClassLoader();
//        String beanClassName = bd.getBeanClassName();
//        try {
//            Class<?> clz = c1.loadClass(beanClassName);
//            //假定类中存在无参构造器
//            return clz.newInstance();
//        } catch (Exception e) {
//            throw new BeanCreationException("create bean for " + beanClassName + " failed", e);
//        }
//    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader();
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
        this.beanPostProcessors.add(postProcessor);
    }

    @Override
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    @Override
    public Object resolveDependency(DependencyDescriptor descriptor) {
        Class<?> typeToMatch = descriptor.getDependencyType();
        for (BeanDefinition bd : this.beanDefinitionMap.values()) {
            resolveBeanClass(bd);
            //这里虽然多加载了,但是我们使用了软引用减少了oom的风险,虽然会对垃圾回收机制造成压力
            Class<?> beanClass = bd.getBeanClass();
            if (typeToMatch.isAssignableFrom(beanClass)) {
                //TODO 可能会存在多个实例符合条件(例如接口的实现类)
                /*
                多个无法抉择抛出异常,否则可以按,名称之类的优先级进行处理 再引入注解
                 */
                return this.getBean(bd.getID());
            }
        }
        return null;
    }

    public void resolveBeanClass(BeanDefinition bd) {
        if (bd.hasBeanClass()) {
            return;
        } else {
            try {
                bd.resolveBeanClass(this.getBeanClassLoader());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("can`t load class" + bd.getBeanClassName());
            }
        }
    }

    @Override
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        BeanDefinition bd = this.getBeanDefinition(name);
        if (bd == null) {
            throw new NoSuchBeanDefinitionException(name);
        }
        resolveBeanClass(bd);
        return bd.getBeanClass();
    }

    @Override
    public List<Object> getBeansByType(Class<?> type) {
        List<Object> result = new ArrayList<>();
        List<String> beanIDs = this.getBeanIDsByType(type);
        for (String beanID : beanIDs) {
            result.add(this.getBean(beanID));
        }
        return result;
    }

    private List<String> getBeanIDsByType(Class<?> type) {
        List<String> result = new ArrayList<>();
        for (String beanName : this.beanDefinitionMap.keySet()) {
            if (type.isAssignableFrom(this.getType(beanName))) {
                result.add(beanName);
            }
        }
        return result;
    }
}
