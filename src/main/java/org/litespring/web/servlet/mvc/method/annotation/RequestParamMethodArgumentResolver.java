package org.litespring.web.servlet.mvc.method.annotation;

import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.TypeConverter;
import org.litespring.core.MethodParameter;
import org.litespring.web.bind.annotation.RequestParam;
import org.litespring.web.servlet.ModelAndView;
import org.litespring.web.servlet.mvc.method.support.HandlerMethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @autor sheltersodom
 * @create 2021-04-02-14:14
 */
public class RequestParamMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(RequestParam.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndView mv,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        RequestParam annotation = parameter.getParameterAnnotation(RequestParam.class);
        String value = annotation.value();
        String param = request.getParameter(value);
        TypeConverter converter = new SimpleTypeConverter();
        Class<?> parameterType = parameter.getParameterType();
        Object arg = converter.convertIfNecessary(param, parameterType);
        return arg;
    }

}
