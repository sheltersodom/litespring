package org.litespring.web.context;

import org.litespring.context.ApplicationContext;

/**
 * @autor sheltersodom
 * @create 2021-04-02-22:29
 */
public interface WebApplicationContext extends ApplicationContext {

    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

    Object createBean(Class<?> beanClass);
}
