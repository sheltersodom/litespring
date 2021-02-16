package org.litespring.test.v5;

import org.litespring.aop.config.AspectInstanceFactory;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.tx.TransactionManager;

import java.lang.reflect.Method;

/**
 * @autor sheltersodom
 * @create 2021-02-16-16:37
 */
public class AbstractV5Test {
    protected BeanFactory getBeanFactory(String config) {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstore-v5");
        reader.loadBeanDefinition(resource);
        return factory;
    }

    protected Method getAdviceMethod(String methodName) throws NoSuchMethodException {
        return TransactionManager.class.getMethod(methodName);
    }

    protected AspectInstanceFactory getAspectInstanceFactory(String targetBeanName) {
        AspectInstanceFactory instanceFactory = new AspectInstanceFactory();
        instanceFactory.setAspectBeanName(targetBeanName);
        return instanceFactory;
    }
}
