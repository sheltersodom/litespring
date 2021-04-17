package org.litespring.web.servlet.mvc.method.support;

import org.litespring.core.MethodParameter;
import org.litespring.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @autor sheltersodom
 * @create 2021-04-02-11:22
 */
public interface HandlerMethodArgumentResolver {
    boolean supportsParameter(MethodParameter parameter);

    Object resolveArgument(MethodParameter parameter, ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, Exception;
}
