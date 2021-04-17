package org.litespring.web.servlet.mvc.method.annotation;

import org.litespring.beans.factory.InitializingBean;
import org.litespring.core.MethodParameter;
import org.litespring.utils.Assert;
import org.litespring.utils.ReflectionUtils;
import org.litespring.web.method.HandlerMethod;
import org.litespring.web.servlet.ModelAndView;
import org.litespring.web.servlet.mvc.method.AbstractHandlerMethodAdapter;
import org.litespring.web.servlet.mvc.method.support.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor sheltersodom
 * @create 2021-04-01-21:45
 */
public class RequestMappingHandlerAdapter extends AbstractHandlerMethodAdapter implements InitializingBean {

    private HandlerMethodArgumentResolverComposite argumentResolvers;
    private List<HandlerMethodReturnValueHandler> returnValueHandlers;

    @Override
    public void afterProperties() {
        if (this.argumentResolvers == null) {
            List<HandlerMethodArgumentResolver> resolvers = getDefaultArgumentResolvers();
            this.argumentResolvers = new HandlerMethodArgumentResolverComposite().addResolvers(resolvers);
        }
        if (this.returnValueHandlers == null) {
            List<HandlerMethodReturnValueHandler> handlers = getDefaultReturnValueHandlers();
            this.returnValueHandlers = handlers;
        }
    }

    private List<HandlerMethodReturnValueHandler> getDefaultReturnValueHandlers() {
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>();
        handlers.add(new ModelAndViewResolverMethodReturnValueHandler());
        return handlers;
    }

    private List<HandlerMethodArgumentResolver> getDefaultArgumentResolvers() {
        List<HandlerMethodArgumentResolver> resolvers = new ArrayList<HandlerMethodArgumentResolver>();
        resolvers.add(new RequestParamMethodArgumentResolver());
        resolvers.add(new SimpleMethodArgumentResolver());
        resolvers.add(new ObjectMethodArgumentResolver());
        return resolvers;
    }


    @Override
    protected boolean supportsInternal(HandlerMethod handler) {
        return true;
    }

    @Override
    protected ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {

        ModelAndView mv = new ModelAndView();

        Object returnValue = invokeForRequest(request, response, mv, handler);

        //判断returnValue是否为空

        handleReturnValue(returnValue, mv, handler.getReturnType(), request, response);

        return mv;
    }

    private void handleReturnValue(Object returnValue, ModelAndView mv, MethodParameter returnType, HttpServletRequest request, HttpServletResponse response) {
        HandlerMethodReturnValueHandler returnValueHandler = getReturnValueHandler(returnType);
        Assert.notNull(returnValueHandler, "Unknown return value type [" + returnType.getParameterType().getName() + "]");
        returnValueHandler.handleReturnValue(returnValue, returnType, request, response, mv);
    }

    private HandlerMethodReturnValueHandler getReturnValueHandler(MethodParameter returnType) {
        for (HandlerMethodReturnValueHandler returnValueHandler : returnValueHandlers) {
            if (returnValueHandler.supportsReturnType(returnType)) {
                return returnValueHandler;
            }
        }
        return null;
    }

    private Object invokeForRequest(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, HandlerMethod handler) {
        Object[] args = getMethodArgumentValues(request, response, mv, handler);
        ReflectionUtils.makeAccessible(handler.getMethod());
        try {
            return handler.getMethod().invoke(handler.getBean(), args);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Object[] getMethodArgumentValues(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, HandlerMethod handler) {
        MethodParameter[] methodParameters = handler.getMethodParameters();
        Object[] args = new Object[methodParameters.length];
        for (int i = 0; i < methodParameters.length; i++) {
            MethodParameter parameter = methodParameters[i];
            if (this.argumentResolvers.supportsParameter(parameter)) {
                try {
                    args[i] = this.argumentResolvers.resolveArgument(parameter, mv, request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }
            if (args[i] == null) {
                throw new IllegalStateException("No suitable resolver for argument");
            }
        }
        return args;
    }
}
