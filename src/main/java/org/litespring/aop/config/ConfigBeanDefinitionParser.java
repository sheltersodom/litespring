package org.litespring.aop.config;

import org.dom4j.Element;
import org.litespring.aop.aspectj.AspectJAfterReturningAdvice;
import org.litespring.aop.aspectj.AspectJAfterThrowingAdvice;
import org.litespring.aop.aspectj.AspectJBeforeAdvice;
import org.litespring.aop.aspectj.AspectJExpressionPointcut;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.support.BeanDefinitionReaderUtils;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor sheltersodom
 * @create 2021-02-16-20:06
 */
public class ConfigBeanDefinitionParser {
    private static final String ASPECT = "aspect";
    private static final String EXPRESSION = "expression";
    private static final String ID = "id";
    private static final String POINTCUT = "pointcut";
    private static final String POINTCUT_REF = "pointcut-ref";
    private static final String REF = "ref";
    private static final String BEFORE = "before";
    private static final String AFTER = "after";
    private static final String AFTER_RETURNING_ELEMENT = "after-returning";
    private static final String AFTER_THROWING_ELEMENT = "after-throwing";
    private static final String AROUND = "around";
    private static final String ASPECT_NAME_PROPERTY = "aspectName";

    public BeanDefinition parse(Element ele, BeanDefinitionRegistry registry) {
        List<Element> childElts = ele.elements();
        for (Element elt : childElts) {
            String localName = elt.getName();
            if (ASPECT.equals(localName)) {
                parseAspect(elt, registry);
            }
        }
        return null;
    }

    private void parseAspect(Element aspectElement, BeanDefinitionRegistry registry) {
        String aspectId = aspectElement.attributeValue(ID);
        String aspectName = aspectElement.attributeValue(REF);

//        List<BeanDefinition> beanDefinitions = new ArrayList<>();
//        List<RuntimeBeanReference> beanReferences = new ArrayList<>();

        List<Element> eleList = aspectElement.elements();
        boolean adviceFoundAlready = false;
        for (int i = 0; i < eleList.size(); i++) {
            Element ele = eleList.get(i);
            if (isAdviceNode(ele)) {
                if (!adviceFoundAlready) {
                    adviceFoundAlready = true;
                    if (!StringUtils.hasText(aspectName)) {
                        return;
                    }
//                    beanReferences.add(new RuntimeBeanReference(aspectName));
                }
                GenericBeanDefinition advisorDefinition = parseAdvice(
                        aspectName, i, aspectElement, ele, registry/*, beanDefinitions, beanReferences*/);
//                beanDefinitions.add(advisorDefinition);
            }
        }
        List<Element> pointcuts = aspectElement.elements(POINTCUT);
        for (Element pointcut : pointcuts) {
            parsePointcut(pointcut, registry);
        }
    }

    private GenericBeanDefinition parsePointcut(Element pointcutElement, BeanDefinitionRegistry registry) {
        String id = pointcutElement.attributeValue(ID);
        String expression = pointcutElement.attributeValue(EXPRESSION);

        GenericBeanDefinition pointcutDefinition = createPointcutDefinition(expression);

        if (StringUtils.hasText(id)) {
            registry.registerBeanDefinition(id, pointcutDefinition);
        } else {
            BeanDefinitionReaderUtils.registerWithGeneratedName(pointcutDefinition, registry);
        }

        return pointcutDefinition;
    }

    private boolean isAdviceNode(Element ele) {
        String name = ele.getName();
        return BEFORE.equals(name) || AFTER.equals(name) || AFTER_THROWING_ELEMENT.equals(name)
                || AFTER_RETURNING_ELEMENT.equals(name) || AROUND.equals(name);
    }

    private GenericBeanDefinition parseAdvice(String aspectName,
                                              int order,
                                              Element aspectElement,
                                              Element adviceElement,
                                              BeanDefinitionRegistry registry/*,
                                              List<BeanDefinition> beanDefinitions,
                                              List<RuntimeBeanReference> beanReferences*/) {
        //创建MethodLocatingFactory
        GenericBeanDefinition methodDefinition = new GenericBeanDefinition(MethodLocatingFactory.class);
        methodDefinition.getPropertyValues().add(new PropertyValue("targetBeanName", aspectName));
        methodDefinition.getPropertyValues().add(new PropertyValue("methodName", adviceElement.attributeValue("method")));
        methodDefinition.setSynthetic(true);

        //创建AspectInstanceFactory
        GenericBeanDefinition aspectFactoryDef = new GenericBeanDefinition(AspectInstanceFactory.class);
        aspectFactoryDef.getPropertyValues().add(new PropertyValue("aspectBeanName", aspectName));
        aspectFactoryDef.setSynthetic(true);

        //解析pointcut
        GenericBeanDefinition adviceDef = createAdviceDefinition(adviceElement, registry,
                aspectName, order, methodDefinition, aspectFactoryDef/*, beanDefinitions, beanReferences*/);
        adviceDef.setSynthetic(true);

        //生成id
        BeanDefinitionReaderUtils.registerWithGeneratedName(adviceDef, registry);

        return adviceDef;
    }

    private GenericBeanDefinition createAdviceDefinition(Element adviceElement,
                                                         BeanDefinitionRegistry registry,
                                                         String aspectName,
                                                         int order,
                                                         GenericBeanDefinition methodDef,
                                                         GenericBeanDefinition aspectFactoryDef/*,
                                                         List<BeanDefinition> beanDefinitions,
                                                         List<RuntimeBeanReference> beanReferences*/) {

        GenericBeanDefinition adviceDefinition = new GenericBeanDefinition(getAdviceClass(adviceElement));
        adviceDefinition.getPropertyValues().add(new PropertyValue(ASPECT_NAME_PROPERTY, aspectName));

        ConstructorArgument cav = adviceDefinition.getConstructorArgument();
        cav.addArgumentValue(methodDef);
        Object pointcut = parsePointcutProperty(adviceElement);
        if (pointcut instanceof BeanDefinition) {
            cav.addArgumentValue(pointcut);
//            beanDefinitions.add((BeanDefinition) pointcut);
        } else if (pointcut instanceof String) {
            RuntimeBeanReference pointcutRef = new RuntimeBeanReference((String) pointcut);
            cav.addArgumentValue(pointcutRef);
//            beanReferences.add(pointcutRef);
        }
        cav.addArgumentValue(aspectFactoryDef);

        return adviceDefinition;
    }

    private Object parsePointcutProperty(Element element) {
        if (element.attribute(POINTCUT) == null && element.attribute(POINTCUT_REF) == null) {
            return null;
        } else if (element.attribute(POINTCUT) != null) {
            String expression = element.attributeValue(POINTCUT);
            GenericBeanDefinition pointcutDefinition = createPointcutDefinition(expression);
            return pointcutDefinition;
        } else if (element.attribute(POINTCUT_REF) != null) {
            String pointcutRef = element.attributeValue(POINTCUT_REF);
            if (!StringUtils.hasText(pointcutRef)) {
                return null;
            }
            return pointcutRef;
        } else {
            return null;
        }
    }

    private GenericBeanDefinition createPointcutDefinition(String expression) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition(AspectJExpressionPointcut.class);
        beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        beanDefinition.setSynthetic(true);
        beanDefinition.getPropertyValues().add(new PropertyValue(EXPRESSION, expression));
        return beanDefinition;

    }

    private Class<?> getAdviceClass(Element aspectElement) {
        String elementName = aspectElement.getName();
        if (BEFORE.equals(elementName)) {
            return AspectJBeforeAdvice.class;
        } /*else if (AFTER.equals(elementName)) {
			return AspectJAfterAdvice.class;
		}*/ else if (AFTER_RETURNING_ELEMENT.equals(elementName)) {
            return AspectJAfterReturningAdvice.class;
        } else if (AFTER_THROWING_ELEMENT.equals(elementName)) {
            return AspectJAfterThrowingAdvice.class;
        } /*else if (AROUND.equals(elementName)) {
			return AspectJAroundAdvice.class;
		}*/ else {
            throw new IllegalArgumentException("Unknown advice kind [" + elementName + "].");
        }
    }
}
