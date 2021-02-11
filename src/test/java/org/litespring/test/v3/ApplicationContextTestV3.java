package org.litespring.test.v3;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.dao.v3.AccountDao;
import org.litespring.dao.v3.ItemDao;
import org.litespring.service.v3.PetStoreService;

/**
 * @autor sheltersodom
 * @create 2021-02-05-16:56
 */
public class ApplicationContextTestV3 {
    @Test
    public void testGetBeanProperty() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v3");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");

        Assert.assertNotNull(petStore);

        Assert.assertNotNull(petStore.getAccountDao());
        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);
        Assert.assertNotNull(petStore.getItemDao());
        Assert.assertTrue(petStore.getItemDao() instanceof ItemDao);

        Assert.assertEquals(petStore.getVersion(), 1);

    }
}
