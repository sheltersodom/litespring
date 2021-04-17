package org.litespring.web.controller.v2;

import org.litespring.stereotype.Controller;
import org.litespring.stereotype.Service;
import org.litespring.web.bind.annotation.RequestMapping;

/**
 * @autor sheltersodom
 * @create 2021-04-15-21:37
 */
@Service
public class PetStoreService {
    @RequestMapping({"/login", "logout", "/abc"})
    public String getIndex(int id) {
        return "index";
    }
}
