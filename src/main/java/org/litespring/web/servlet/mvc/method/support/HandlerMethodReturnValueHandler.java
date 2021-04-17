package org.litespring.web.servlet.mvc.method.support;

import org.litespring.core.MethodParameter;
import org.litespring.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @autor sheltersodom
 * @create 2021-04-01-22:29
 */
public interface HandlerMethodReturnValueHandler {
    void handleReturnValue(Object returnValue, MethodParameter returnType, HttpServletRequest request, HttpServletResponse response, ModelAndView mv);

    boolean supportsReturnType(MethodParameter returnType);
}
