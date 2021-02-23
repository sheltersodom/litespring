package org.litespring.service.v8;

import org.litespring.beans.factory.annotation.Autowired;
import org.litespring.stereotype.Component;

/**
 * @autor sheltersodom
 * @create 2021-02-23-14:09
 */
@Component
public class PetStoreC {
    @Autowired
    PetStoreA petStoreA;
    @Autowired
    PetStoreB petStoreB;

    public PetStoreA getPetStoreA() {
        return petStoreA;
    }
}
