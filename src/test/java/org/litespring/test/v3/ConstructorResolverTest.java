package org.litespring.test.v3;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.support.ConstructorResolver;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.service.v3.PetStoreService;

/**
 * @autor sheltersodom
 * @create 2021-02-08-23:29
 */
public class ConstructorResolverTest {
    @Test
    public void testAutowireConstructor() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinition(new ClassPathResource("petstore-v3"));
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        ConstructorResolver resolver = new ConstructorResolver(factory);
        PetStoreService petStore = (PetStoreService) resolver.autowireConstructor(bd);

        Assert.assertEquals(1, petStore.getVersion());
        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertNotNull(petStore.getItemDao());
    }
}
