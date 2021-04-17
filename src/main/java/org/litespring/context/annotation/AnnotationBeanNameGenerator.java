package org.litespring.context.annotation;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.annotation.AnnotatedBeanDefinition;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.BeanNameGenerator;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.stereotype.Component;
import org.litespring.stereotype.Controller;
import org.litespring.stereotype.Repository;
import org.litespring.stereotype.Service;
import org.litespring.utils.ClassUtils;
import org.litespring.utils.StringUtils;

import java.beans.Introspector;
import java.util.Set;

/**
 * @autor sheltersodom
 * @create 2021-02-11-16:23
 */
public class AnnotationBeanNameGenerator implements BeanNameGenerator {

    @Override
    public String generateBeanName(BeanDefinition bd, BeanDefinitionRegistry registry) {
        if (bd instanceof AnnotatedBeanDefinition) {
            String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) bd);
            if (StringUtils.hasText(beanName)) {
                return beanName;
            }
        }
        return bulidDefaultBeanName(bd, registry);
    }


    protected String determineBeanNameFromAnnotation(AnnotatedBeanDefinition annotatedDef) {
        AnnotationMetadata amd = annotatedDef.getMetadata();
        Set<String> types = amd.getAnnotationTypes();
        String beanName = null;
        for (String type : types) {
            AnnotationAttributes attributes = amd.getAnnotationAttributes(type);
            if (isStereotypeWithName(type, attributes)) {
                Object value = attributes.get("value");
                if (value instanceof String) {
                    String strVal = (String) value;
                    if (StringUtils.hasLength(strVal))
                        beanName = strVal;
                }
            }
        }
        return beanName;
    }

    protected boolean isStereotypeWithName(String annotationType, AnnotationAttributes attributes) {
        boolean isStereotype = annotationType.equals(Component.class.getName())
                || annotationType.equals(Controller.class.getName())
                || annotationType.equals(Service.class.getName())
                || annotationType.equals(Repository.class.getName());
        return isStereotype && attributes != null && attributes.containsKey("value");
    }

    protected String bulidDefaultBeanName(BeanDefinition bd, BeanDefinitionRegistry registry) {
        return bulidDefaultBeanName(bd);
    }

    protected String bulidDefaultBeanName(BeanDefinition bd) {
        String shortClassName = ClassUtils.getShortName(bd.getBeanClassName());
        return Introspector.decapitalize(shortClassName);
    }
}
