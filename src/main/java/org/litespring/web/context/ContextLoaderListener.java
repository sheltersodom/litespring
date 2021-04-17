package org.litespring.web.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @autor sheltersodom
 * @create 2021-04-14-20:00
 */
public class ContextLoaderListener extends ContextLoader implements ServletContextListener {

    public ContextLoaderListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        initWebApplicationContext(event.getServletContext());
    }



    @Override
    public void contextDestroyed(ServletContextEvent event) {
        //TODO:生命周期的销毁

    }
}
