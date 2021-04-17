package org.litespring.web.servlet.mvc.method.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.core.MethodParameter;
import org.litespring.utils.Assert;
import org.litespring.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

/**
 * @autor sheltersodom
 * @create 2021-04-01-22:10
 */
public class HandlerMethodArgumentResolverComposite implements HandlerMethodArgumentResolver {

    protected final Log logger = LogFactory.getLog(getClass());

    private final List<HandlerMethodArgumentResolver> argumentResolvers = new LinkedList<>();


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return getArgumentResolver(parameter) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndView mv,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        HandlerMethodArgumentResolver resolver = getArgumentResolver(parameter);
        Assert.notNull(resolver, "Unknown parameter type [" + parameter.getParameterType().getName() + "]");
        return resolver.resolveArgument(parameter, mv, request, response);
    }

    private HandlerMethodArgumentResolver getArgumentResolver(MethodParameter parameter) {
        for (HandlerMethodArgumentResolver methodArgumentResolver : this.argumentResolvers) {
            if (methodArgumentResolver.supportsParameter(parameter)) {
                return methodArgumentResolver;
            }
        }
        return null;
    }

    public HandlerMethodArgumentResolverComposite addResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        this.argumentResolvers.addAll(resolvers);
        return this;
    }
}
