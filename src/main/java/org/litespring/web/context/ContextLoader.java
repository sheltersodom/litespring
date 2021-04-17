package org.litespring.web.context;

import org.litespring.beans.BeanUtils;
import org.litespring.utils.ClassUtils;
import org.litespring.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletContext;
import java.net.DatagramSocket;

/**
 * @autor sheltersodom
 * @create 2021-04-14-20:02
 */
public class ContextLoader {

    private WebApplicationContext context;
    private static final String CONTEXT_CLASS_PARAM = "contextClass";
    private static final String DEFAULT_LOAD_STRATEGY = XmlWebApplicationContext.class.getName();

    public void initWebApplicationContext(ServletContext servletContext) {
        if (this.context == null) {
            this.context = createWebApplicationContext(servletContext);
        }
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);
    }

    private WebApplicationContext createWebApplicationContext(ServletContext servletContext) {
        Class<?> contextClass = determineContextClass(servletContext);
        if (!WebApplicationContext.class.isAssignableFrom(contextClass)) {
            throw new IllegalStateException("Custom context class [" + contextClass.getName() +
                    "] is not of type [" + WebApplicationContext.class.getName() + "]");
        }
        return (WebApplicationContext) BeanUtils.instantiateClass(contextClass);
    }

    private Class<?> determineContextClass(ServletContext servletContext) {
        String contextClassName = servletContext.getInitParameter(CONTEXT_CLASS_PARAM);
        if (contextClassName != null) {
            try {
                return ClassUtils.forName(contextClassName, ClassUtils.getDefaultClassLoader());
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Failed to load custom context class [" + contextClassName + "]", e);
            }
        } else {
            try {
                return ClassUtils.forName(DEFAULT_LOAD_STRATEGY, ClassUtils.getDefaultClassLoader());
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Failed to load default context class [" + contextClassName + "]", e);
            }
        }
    }
}
