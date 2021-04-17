package org.litespring.web.servlet.mvc.method.annotation;

import org.litespring.core.MethodParameter;
import org.litespring.web.servlet.ModelAndView;
import org.litespring.web.servlet.View;
import org.litespring.web.servlet.mvc.method.support.HandlerMethodReturnValueHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @autor sheltersodom
 * @create 2021-04-15-18:26
 */
public class ModelAndViewResolverMethodReturnValueHandler implements HandlerMethodReturnValueHandler {
    @Override
    public void handleReturnValue(Object returnValue,
                                  MethodParameter returnType,
                                  HttpServletRequest request,
                                  HttpServletResponse response,
                                  ModelAndView mv) {
        if (returnValue instanceof String || returnValue instanceof View) {
            mv.setView(returnValue);
        } else if (returnValue instanceof ModelAndView) {
            ModelAndView mav = (ModelAndView) returnValue;
            mv.setView(mav.getView());
            mv.addAllObjects(mav.getModelMap());
        } else {
            throw new IllegalStateException("The current type is not supported");
        }
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return true;
    }
}
