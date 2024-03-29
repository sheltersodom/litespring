package org.litespring.service.v9;

import org.litespring.beans.factory.annotation.Autowired;
import org.litespring.stereotype.Component;
import org.litespring.util.MessageTracker;

/**
 * @autor sheltersodom
 * @create 2021-02-23-14:09
 */
@Component
public class PetStoreA {
    @Autowired
    private PetStoreB petStoreB;

    public PetStoreB getPetStoreB() {
        return petStoreB;
    }

    public void placeOrder() {
        System.out.println("place order");
        MessageTracker.addMsg("place order");
    }
}
