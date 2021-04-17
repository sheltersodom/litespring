package org.litespring.web.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.web.method.HandlerMethod;
import org.litespring.web.servlet.mvc.method.RequestMappingInfo;
import org.litespring.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

/**
 * @autor sheltersodom
 * @create 2021-04-15-22:24
 */
public class HandlerMethodsTest {
    @Test
    public void initHandlerMethodsTest() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstoreWeb-v2");
        reader.loadBeanDefinition(resource);

        RequestMappingHandlerMapping mapping = new RequestMappingHandlerMapping();
        mapping.setBeanFactory(factory);
        mapping.afterProperties();

        Map<String, RequestMappingInfo> urlMap = mapping.getUrlMap();
        {
            Assert.assertTrue(urlMap.containsKey("/login"));
            Assert.assertTrue(urlMap.containsKey("/logout"));
            Assert.assertTrue(urlMap.containsKey("/abc"));
        }
        RequestMappingInfo mappingInfo = urlMap.get("/login");
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();
        {
            Assert.assertTrue(handlerMethods.containsKey(mappingInfo));
        }
    }
}
