package org.litespring.web.servlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor sheltersodom
 * @create 2021-03-30-16:21
 */
public class HandlerExecutionChain {
    private static final Log logger = LogFactory.getLog(HandlerExecutionChain.class);

    private final Object handler;

    private List<HandlerInterceptor> interceptorList;

    private int interceptorIndex = -1;

    public HandlerExecutionChain(Object handler) {
        this(handler, null);
    }

    /**
     * 暂时只需要包装handlerMethod
     *
     * @param handler
     * @param interceptorList
     */
    public HandlerExecutionChain(Object handler, List<HandlerInterceptor> interceptorList) {
        this.handler = handler;
        this.interceptorList = interceptorList == null ? new ArrayList<>() : interceptorList;
    }

    public void addInterceptor(HandlerInterceptor interceptor) {
        interceptorList.add(interceptor);
    }

    public void addInterceptors(List<HandlerInterceptor> interceptors) {
        if (interceptors == null) return;
        interceptorList.addAll(interceptors);
    }

    public List<HandlerInterceptor> getInterceptors() {
        return this.interceptorList;
    }

    /**
     * 拦截器前处理
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    boolean applyPreHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        for (int i = 0; i < getInterceptors().size(); i++) {
            HandlerInterceptor interceptor = getInterceptors().get(i);
            if (!interceptor.preHandler(request, response, this.handler)) {
                triggerAfterCompletion(request, response, null);
                return false;
            }
            this.interceptorIndex = i;
        }
        return true;
    }

    /**
     * 拦截器后处理
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    boolean applyPostHandle(HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws Exception {
        for (int i = getInterceptors().size() - 1; i >= 0; i--) {
            HandlerInterceptor interceptor = getInterceptors().get(i);
            interceptor.postHandle(request, response, model);
        }
        return true;
    }


    /**
     * 拦截器结束视图处理
     *
     * @param request
     * @param response
     * @param ex
     */
    public void triggerAfterCompletion(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        for (int i = this.interceptorIndex; i >= 0; i--) {
            try {
                HandlerInterceptor interceptor = getInterceptors().get(i);
                interceptor.afterCompletion(request, response, ex);
            } catch (Exception e) {
                logger.error("HandlerInterceptor.afterCompletion threw exception" + e);
            }
        }
    }


    public Object getHandler() {
        return handler;
    }
}
