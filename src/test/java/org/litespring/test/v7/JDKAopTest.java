package org.litespring.test.v7;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.service.v10.IPetStoreService;
import org.litespring.service.v10.PetStoreService;
import org.litespring.service.v9.PetStoreA;
import org.litespring.util.MessageTracker;

import java.util.List;

/**
 * @autor sheltersodom
 * @create 2021-03-17-9:55
 */
public class JDKAopTest {
    @Before
    public void setUp() {
        MessageTracker.clearMsgs();
    }

    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v10");
        IPetStoreService petStore = (IPetStoreService) ctx.getBean("petStore");

        petStore.placeOrder();

//        List<String> msgs = MessageTracker.getMsgs();
//        Assert.assertEquals(3, msgs.size());
//        Assert.assertEquals("start tx", msgs.get(0));
//        Assert.assertEquals("place order", msgs.get(1));
//        Assert.assertEquals("commit tx", msgs.get(2));
    }
}
