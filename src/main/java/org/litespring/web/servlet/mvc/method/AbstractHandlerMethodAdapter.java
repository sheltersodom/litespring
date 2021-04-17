package org.litespring.web.servlet.mvc.method;

import org.litespring.web.method.HandlerMethod;
import org.litespring.web.servlet.HandlerAdapter;
import org.litespring.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @autor sheltersodom
 * @create 2021-04-01-21:46
 */
public abstract class AbstractHandlerMethodAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        //此类只处理handlermethod类型
        return handler instanceof HandlerMethod && supportsInternal((HandlerMethod) handler);
    }

    protected abstract boolean supportsInternal(HandlerMethod handler);

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return handleInternal(request, response, (HandlerMethod) handler);
    }

    protected abstract ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler);
}
