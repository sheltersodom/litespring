package org.litespring.web.context.support;

import org.litespring.utils.Assert;
import org.litespring.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * @autor sheltersodom
 * @create 2021-04-14-20:50
 */
public class WebApplicationContextUtils {
    public static final String attrName = WebApplicationContext.class.getName() + ".ROOT";

    public static WebApplicationContext getWebApplicationContext(ServletContext sc) {
        Assert.notNull(sc, "ServletContext must not be null");
        Object attr = sc.getAttribute(attrName);
        if(!(attr instanceof WebApplicationContext)){
            throw new IllegalStateException("this context has wrong type");
        }
        return (WebApplicationContext) attr;
    }
}
