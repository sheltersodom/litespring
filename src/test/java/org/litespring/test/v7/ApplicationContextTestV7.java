package org.litespring.test.v7;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.service.v7.PetStoreA;
import org.litespring.service.v7.PetStoreB;
import org.litespring.util.MessageTracker;

import java.util.List;

/**
 * @autor sheltersodom
 * @create 2021-02-05-16:56
 */
public class ApplicationContextTestV7 {

    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v7");
        PetStoreA petStore = (PetStoreA) ctx.getBean("petStoreA");

        Assert.assertTrue(petStore instanceof PetStoreA);
        Assert.assertTrue(petStore.getPetStoreB() instanceof PetStoreB);
    }
}
