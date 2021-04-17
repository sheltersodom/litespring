package org.litespring.web.servlet.mvc.method.support;

import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.TypeConverter;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.MethodParameter;
import org.litespring.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @autor sheltersodom
 * @create 2021-04-02-16:24
 */
public class SimpleMethodArgumentResolver implements HandlerMethodArgumentResolver {
    Set<Class<?>> supports = new LinkedHashSet<>();

    {
        supports.add(Boolean.class);
        supports.add(boolean.class);
        supports.add(Integer.class);
        supports.add(int.class);
    }


    /**
     * 目前只支持boolean类型与int类型的转化
     *
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> parameterType = parameter.getParameterType();
        return supports.contains(parameterType) || parameterType.isAssignableFrom(String.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndView mv,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        TypeConverter converter = new SimpleTypeConverter();
        Class<?> parameterType = parameter.getParameterType();
        String parameterName = parameter.getParameterName();
        String requestParameter = request.getParameter(parameterName);
        return converter.convertIfNecessary(requestParameter, parameterType);
    }
}
