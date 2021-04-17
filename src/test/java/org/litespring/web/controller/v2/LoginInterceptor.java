package org.litespring.web.controller.v2;

import org.litespring.web.servlet.HandlerInterceptor;
import org.litespring.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @autor sheltersodom
 * @create 2021-04-16-22:15
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandler(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getRequestURI().contains("login")) return true;
        if (request.getSession().getAttribute("user") != null) return true;
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Exception ex) {

    }
}
