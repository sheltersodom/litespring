package org.litespring.web.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.web.servlet.HandlerInterceptor;
import org.litespring.web.servlet.handler.MappedInterceptor;

/**
 * @autor sheltersodom
 * @create 2021-04-16-22:06
 */
public class InterceptorTest {
    @Test
    public void interceptorParseTest() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstoreWeb-v1");
        reader.loadBeanDefinition(resource);

        MappedInterceptor interceptor = (MappedInterceptor) factory.getBean("org.litespring.web.servlet.handler.MappedInterceptor#0");

        Assert.assertTrue(interceptor.getInterceptor() instanceof HandlerInterceptor);
        Assert.assertTrue(interceptor.getPathPatterns().size() == 1);
        Assert.assertTrue(interceptor.getPathPatterns().get(0).equals("/index"));

    }
}
