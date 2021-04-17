package org.litespring.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @autor sheltersodom
 * @create 2021-04-01-21:40
 */
public interface View {
    /**
     * Render the view given the specified model.
     * <p>The first step will be preparing the request: In the JSP case,
     * this would mean setting model objects as request attributes.
     * The second step will be the actual rendering of the view,
     * for example including the JSP via a RequestDispatcher.
     * @param model Map with name Strings as keys and corresponding model
     * objects as values (Map can also be {@code null} in case of empty model)
     * @param request current HTTP request
     * @param response HTTP response we are building
     * @throws Exception if rendering failed
     */
    void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
