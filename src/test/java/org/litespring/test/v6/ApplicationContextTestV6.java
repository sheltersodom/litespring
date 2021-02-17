package org.litespring.test.v6;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.dao.v5.AccountDao;
import org.litespring.dao.v5.ItemDao;
import org.litespring.service.v5.PetStoreService;
import org.litespring.service.v6.IPetStoreService;
import org.litespring.util.MessageTracker;

import java.util.List;

/**
 * @autor sheltersodom
 * @create 2021-02-05-16:56
 */
public class ApplicationContextTestV6 {
    @Before
    public void setUp() {
        MessageTracker.clearMsgs();
    }

    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v6");
        IPetStoreService petStore = (IPetStoreService) ctx.getBean("petStore");

        Assert.assertNotNull(petStore);

        petStore.placeOrder();

        List<String> msgs = MessageTracker.getMsgs();
        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));
    }
}
