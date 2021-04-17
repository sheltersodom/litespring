package org.litespring.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @autor sheltersodom
 * @create 2021-03-30-16:23
 */
public interface HandlerInterceptor {

    boolean preHandler(HttpServletRequest request, HttpServletResponse response, Object handler);

    void postHandle(HttpServletRequest request, HttpServletResponse response, ModelAndView mv);

    void afterCompletion(HttpServletRequest request, HttpServletResponse response, Exception ex);
}
