package org.litespring.web.servlet;

/**
 * @autor sheltersodom
 * @create 2021-03-30-16:14
 */
public interface ViewResolver {
    View resolveViewName(String viewName);
}
