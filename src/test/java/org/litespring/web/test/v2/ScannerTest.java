package org.litespring.web.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.annotation.ScannedGenericBeanDefinition;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.stereotype.Controller;
import org.litespring.stereotype.Repository;
import org.litespring.stereotype.Service;

/**
 * @autor sheltersodom
 * @create 2021-04-15-22:14
 */
public class ScannerTest {
    @Test
    public void testParseScanedBean() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new ClassPathResource("petstoreWeb-v2");
        reader.loadBeanDefinition(resource);

        {
            String annotation = Controller.class.getName();
            BeanDefinition bd = factory.getBeanDefinition("petStore");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) bd;
            AnnotationMetadata amd = sbd.getMetadata();

            Assert.assertTrue(amd.hasAnnotation(annotation));
            AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
            Assert.assertEquals("petStore", attributes.get("value"));
        }
        {
            String annotation = Service.class.getName();
            BeanDefinition bd = factory.getBeanDefinition("petStoreService");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) bd;
            AnnotationMetadata amd = sbd.getMetadata();

            Assert.assertTrue(amd.hasAnnotation(annotation));
        }
        {
            String annotation = Repository.class.getName();
            BeanDefinition bd = factory.getBeanDefinition("petStoreDao");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition) bd;
            AnnotationMetadata amd = sbd.getMetadata();

            Assert.assertTrue(amd.hasAnnotation(annotation));
        }
    }
}
