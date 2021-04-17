package org.litespring.web.servlet.config;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.factory.support.BeanDefinitionReaderUtils;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.utils.Assert;
import org.litespring.web.servlet.handler.MappedInterceptor;
import org.dom4j.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

/**
 * @autor sheltersodom
 * @create 2021-04-16-20:57
 */
public class InterceptorsBeanDefinitionParser {
    private static final String INTERCEPTOR = "interceptor";
    private static final String MAPPING = "mapping";
    private static final String EX_MAPPING = "exclude-mapping";
    private static final String BEAN = "bean";
    private static final String PATH = "path";


    public void parse(Element element, XmlBeanDefinitionReader reader, BeanDefinitionRegistry registry) {

        Iterator iterator = element.elementIterator();
        GenericBeanDefinition mappedInterceptorDef = new GenericBeanDefinition(MappedInterceptor.class);
        List<String> includePatterns = new ArrayList<>();
        List<String> excludePatterns = new ArrayList<>();
        BeanDefinition interceptorBean = null;


        while (iterator.hasNext()) {
            Element propElem = (Element) iterator.next();
            if (INTERCEPTOR.equals(propElem.getName())) {
                Iterator interceptorIterator = propElem.elementIterator();
                while (interceptorIterator.hasNext()) {
                    Element ele = (Element) interceptorIterator.next();
                    if (MAPPING.equals(ele.getName())) {
                        includePatterns.add(ele.attributeValue(PATH));
                    } else if (EX_MAPPING.equals(ele.getName())) {
                        excludePatterns.add(ele.attributeValue(PATH));
                    } else if (BEAN.equals(ele.getName())) {
                        interceptorBean = reader.parseDefalutElement(ele, false);
                    }
                }
            }
        }

        ConstructorArgument constructorArgument = mappedInterceptorDef.getConstructorArgument();
        constructorArgument.addArgumentValue(includePatterns);
        constructorArgument.addArgumentValue(excludePatterns);
        constructorArgument.addArgumentValue(interceptorBean);

        BeanDefinitionReaderUtils.registerWithGeneratedName(mappedInterceptorDef, registry);
    }
}
