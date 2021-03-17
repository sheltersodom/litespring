package org.litespring.service.v10;

import org.litespring.beans.factory.annotation.Autowired;
import org.litespring.stereotype.Component;
import org.litespring.util.MessageTracker;

/**
 * @autor sheltersodom
 * @create 2021-03-17-9:57
 */
@Component(value = "petStore")
public class PetStoreService implements IPetStoreService {
    @Autowired
    private IDaoStoreService daoStoreService;

    public PetStoreService() {
    }

    @Override
    public void placeOrder() {
        daoStoreService.placeOrder();
        System.out.println("place order");
        MessageTracker.addMsg("place order");
    }
}
