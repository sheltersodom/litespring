package org.litespring.service.v10;


import org.litespring.beans.factory.annotation.Autowired;
import org.litespring.stereotype.Component;
import org.litespring.util.MessageTracker;

/**
 * @autor sheltersodom
 * @create 2021-02-08-22:34
 */
@Component(value = "daoStore")
public class DaoStoreService implements IDaoStoreService {

    @Autowired
    private IPetStoreService petStoreService;

    public DaoStoreService() {
    }

    public void placeOrder() {
        System.out.println("place order");
        MessageTracker.addMsg("place order");
    }

}
