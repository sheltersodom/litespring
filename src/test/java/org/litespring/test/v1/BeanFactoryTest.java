package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.service.v1.PetStoreService;

/**
 * @autor sheltersodom
 * @create 2021-02-04-22:28
 */
public class BeanFactoryTest {
//    @Test
//    public void testGetBean() {
//        BeanFactory factory = new DefaultBeanFactory("petstore-v1");
//        BeanDefinition bd = factory.getBeanDefinition("petStore");
//        Assert.assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());
//        PetStoreService petStore = (PetStoreService) factory.getBean("petStore");
//        Assert.assertNotNull(petStore);
//    }
    DefaultBeanFactory factory = null;
    XmlBeanDefinitionReader reader = null;

    @Before
    public void setUp() {
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);

    }

    @Test
    public void testGetBean_opt() {
        reader.loadBeanDefinition("petstore-v1");
        BeanDefinition bd = factory.getBeanDefinition("petStore");
        Assert.assertEquals("org.litespring.service.v1.PetStoreService", bd.getBeanClassName());
        PetStoreService petStore = (PetStoreService) factory.getBean("petStore");
        Assert.assertNotNull(petStore);
    }

    @Test
    public void testGetBean_signal() {
        reader.loadBeanDefinition("petstore-v1");
        BeanDefinition bd = factory.getBeanDefinition("petStore");
        Assert.assertTrue(bd.isSingleton());
        Assert.assertFalse(bd.isPrototype());
        Assert.assertEquals(BeanDefinition.SCOPE_DEFAULT, bd.getScope());

        PetStoreService petStore = (PetStoreService) factory.getBean("petStore");
        Assert.assertNotNull(petStore);
        PetStoreService petStore1 = (PetStoreService) factory.getBean("petStore");
        Assert.assertTrue(petStore.equals(petStore1));
    }

    @Test
    public void testInvalidBean() {
        reader.loadBeanDefinition("petstore-v1");
        try {
            factory.getBean("invalidBean");
        } catch (BeanCreationException e) {
            return;
        }
        Assert.fail("except BeanCreationException");
    }

    @Test
    public void testInvalidXML() {
        try {
            reader.loadBeanDefinition("xxxx");
        } catch (BeanDefinitionStoreException e) {
            return;
        }
        Assert.fail("except BeanDefinitionStoreException");
    }
}
