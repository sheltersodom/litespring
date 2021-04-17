package org.litespring.web.controller.v1;

import org.litespring.stereotype.Controller;
import org.litespring.web.bind.annotation.RequestMapping;

/**
 * @autor sheltersodom
 * @create 2021-04-15-21:37
 */
@Controller
public class PetStoreController {
    @RequestMapping({"/login", "logout", "/abc"})
    public String getIndex(int id) {
        return "index";
    }
}
