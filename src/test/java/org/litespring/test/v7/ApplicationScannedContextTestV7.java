package org.litespring.test.v7;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.service.v8.PetStoreA;
import org.litespring.service.v8.PetStoreB;

/**
 * @autor sheltersodom
 * @create 2021-02-05-16:56
 */
public class ApplicationScannedContextTestV7 {

    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v8");
        PetStoreA petStore = (PetStoreA) ctx.getBean("petStoreA");

        Assert.assertTrue(petStore instanceof PetStoreA);
        Assert.assertTrue(petStore.getPetStoreB() instanceof PetStoreB);
    }
}
