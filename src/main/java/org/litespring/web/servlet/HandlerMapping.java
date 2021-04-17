package org.litespring.web.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * @autor sheltersodom
 * @create 2021-03-30-16:13
 */
public interface HandlerMapping {
    HandlerExecutionChain getHandler(HttpServletRequest request);
}
