package org.litespring.web.servlet.mvc.method.support;

import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.TypeConverter;
import org.litespring.core.MethodParameter;
import org.litespring.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;

/**
 * @autor sheltersodom
 * @create 2021-04-02-16:35
 */
public class ObjectMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndView mv,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        Class<?> parameterType = parameter.getParameterType();
        Object result = resolveArgument(parameter.getParameterType(), mv, request, response);

        return result;
    }

    private Object resolveArgument(Class<?> classType,
                                  ModelAndView mv,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        Object result = classType.newInstance();
        TypeConverter converter = new SimpleTypeConverter();
        for (Field field : classType.getDeclaredFields()) {
            Class<?> type = field.getType();
            String name = field.getName();
            String parameterName = request.getParameter(name);
            Object convertedField = converter.convertIfNecessary(parameterName, type);
            if (!(convertedField instanceof String)) {
                convertedField = resolveArgument(type, mv, request, response);
            }
            field.set(result, convertedField);
        }
        return result;
    }
}
