package org.litespring.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @autor sheltersodom
 * @create 2021-03-30-16:13
 */
public interface HandlerAdapter {
    /**
     * 判断是否支持对应的handler
     * @param handler
     * @return
     */
    boolean supports(Object handler);

    /**
     * 处理方法并返回结果
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
