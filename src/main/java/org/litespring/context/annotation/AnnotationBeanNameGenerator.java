package org.litespring.context.annotation;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.annotation.AnnotatedBeanDefinition;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.BeanNameGenerator;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.stereotype.Component;
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
            if (Component.class.getName().equals(type)) {
                if (attributes.get("value") != null) {
                    beanName = attributes.getString("value");
                }
            }
        }
        return beanName;
    }

    protected String bulidDefaultBeanName(BeanDefinition bd, BeanDefinitionRegistry registry) {
        return bulidDefaultBeanName(bd);
    }

    protected String bulidDefaultBeanName(BeanDefinition bd) {
        String shortClassName = ClassUtils.getShortName(bd.getBeanClassName());
        return Introspector.decapitalize(shortClassName);
    }
}
