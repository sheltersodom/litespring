package org.litespring.service.v9;

import org.litespring.beans.factory.annotation.Autowired;
import org.litespring.stereotype.Component;
import org.litespring.util.MessageTracker;

/**
 * @autor sheltersodom
 * @create 2021-02-23-14:09
 */
@Component
public class PetStoreB {
    @Autowired
    private PetStoreA petStoreA;

    public void placeOrder() {
        System.out.println("place orderB");
        MessageTracker.addMsg("place order");
    }
}
