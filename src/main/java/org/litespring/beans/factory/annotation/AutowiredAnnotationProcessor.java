package org.litespring.beans.factory.annotation;

import org.litespring.beans.BeansException;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.AutowireCapableBeanFactory;
import org.litespring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.litespring.core.annotation.AnnotationUtils;
import org.litespring.utils.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @autor sheltersodom
 * @create 2021-02-13-21:26
 */
public class AutowiredAnnotationProcessor implements InstantiationAwareBeanPostProcessor {
    private AutowireCapableBeanFactory beanFactory;
    private String requiredParameterName = "required";
    private boolean requiredParameterValue = true;
    private final Set<Class<? extends Annotation>> autowireAnnotationTypes = new LinkedHashSet<>();

    public AutowiredAnnotationProcessor() {
        this.autowireAnnotationTypes.add(Autowired.class);
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public InjectionMetadata bulidAutowiringMetadata(Class<?> clazz) {
        Class<?> targetClass = clazz;
        List<InjectionElement> elements = new LinkedList<>();

        do {
            LinkedList<InjectionElement> currElements = new LinkedList<>();
            for (Field field : targetClass.getDeclaredFields()) {
                Annotation ann = findAutowiredAnnotation(field);
                if (ann != null) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                    boolean required = determineRequiredStatus(ann);
                    currElements.add(new AutowiredFieldElement(field, required, beanFactory));
                }
            }
            for (Method method : targetClass.getDeclaredMethods()) {
                //TODO 处理方法注入
            }
            elements.addAll(0, currElements);
            targetClass = targetClass.getSuperclass();
        } while (targetClass != null && targetClass != Object.class);
        return new InjectionMetadata(clazz, elements);
    }

    /**
     * 通过调用注解中的required方法判断参数的值,注解中的属性几乎都使用方法代替,这也使得调用判断更加方便
     *
     * @param ann
     * @return
     */
    private boolean determineRequiredStatus(Annotation ann) {
        try {
            Method method = ReflectionUtils.findMethod(ann.annotationType(), this.requiredParameterName);
            if (method == null) {
                return true;
            }
            return this.requiredParameterValue == (Boolean) ReflectionUtils.invokeMethod(method, ann);
        } catch (Exception e) {
            return true;
        }
    }

    private Annotation findAutowiredAnnotation(AccessibleObject ao) {
        for (Class<? extends Annotation> type : this.autowireAnnotationTypes) {
            Annotation ann = AnnotationUtils.getAnnotation(ao, type);
            if (ann != null) {
                return ann;
            }
        }
        return null;
    }

    @Override
    public void postProcessPropertyValues(Object bean, String beanName) throws BeansException {
        InjectionMetadata metadata = bulidAutowiringMetadata(bean.getClass());
        try {
            metadata.inject(bean);
        } catch (Exception e) {
            throw new BeanCreationException(beanName, "Injection of autowired dependencies failed", e);
        }
    }

    @Override
    public Object beforeInstantiation(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public boolean afterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }


    @Override
    public Object beforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object afterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
