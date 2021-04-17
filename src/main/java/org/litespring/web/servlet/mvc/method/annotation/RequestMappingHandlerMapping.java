package org.litespring.web.servlet.mvc.method.annotation;

import net.sf.cglib.transform.MethodFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.beans.BeansException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.BeanFactoryAware;
import org.litespring.context.ApplicationContext;
import org.litespring.core.annotation.AnnotationUtils;
import org.litespring.stereotype.Controller;
import org.litespring.utils.ClassUtils;
import org.litespring.utils.ReflectionUtils;
import org.litespring.web.bind.annotation.RequestMapping;
import org.litespring.web.method.HandlerMethod;
import org.litespring.web.servlet.HandlerExecutionChain;
import org.litespring.web.servlet.HandlerMapping;
import org.litespring.web.servlet.handler.AbstractHandlerMapping;
import org.litespring.web.servlet.mvc.method.RequestMappingInfo;
import org.litespring.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * requestMapping组件的注册
 *
 * @autor sheltersodom
 * @create 2021-03-30-16:39
 */
public class RequestMappingHandlerMapping extends AbstractHandlerMapping {
    private final Map<RequestMappingInfo, HandlerMethod> handlerMethods = new LinkedHashMap<>();
    private final Map<String, RequestMappingInfo> urlMap = new LinkedHashMap<>();


    protected final Log logger = LogFactory.getLog(HandlerMethod.class);

    public RequestMappingHandlerMapping() {
    }


    public Map<RequestMappingInfo, HandlerMethod> getHandlerMethods() {
        return handlerMethods;
    }

    public Map<String, RequestMappingInfo> getUrlMap() {
        return urlMap;
    }

    @Override
    protected void initHandlerMethods() {
        List<String> beanNames = getFactory().getBeanIDsByType(Object.class);
        for (String beanName : beanNames) {
            if (isHandler(getFactory().getType(beanName))) {
                detectHandlerMethods(beanName);
            }
        }
    }

    private boolean isHandler(Class<?> beanType) {
        return AnnotationUtils.findAnnotation(beanType, Controller.class) != null;
    }

    protected void detectHandlerMethods(final String beanName) {
        Class<?> handler = getFactory().getType(beanName);
        Set<Method> declaredMethods = ReflectionUtils.getAllMethods(handler);

        for (Method method : declaredMethods) {
            RequestMappingInfo mapping = getMappingForMethod(method);
            registerHandlerMethod(beanName, method, mapping);
        }
    }

    public RequestMappingInfo getMappingForMethod(Method method/*, Class<?> handlerType*/) {
        RequestMappingInfo info = null;
        RequestMapping methodAnnotation = AnnotationUtils.findAnnotation(method, RequestMapping.class);
        if (methodAnnotation != null) {
            info = new RequestMappingInfo(methodAnnotation.value());
        }
        return info;
    }

    public void registerHandlerMethod(String beanName, Method method, RequestMappingInfo mapping) {
        HandlerMethod newHandlerMethod = createHandlerMethod(beanName, method);
        HandlerMethod oldHandlerMethod = handlerMethods.get(mapping);
        //如果存在对应的路径,我们需要进行异常处理
        if (oldHandlerMethod != null && !oldHandlerMethod.equals(newHandlerMethod)) {
            throw new IllegalStateException("Ambiguous mapping found. Cannot map '" + newHandlerMethod.getBean()
                    + "' bean method \n" + newHandlerMethod + "\nto " + mapping + ": There is already '"
                    + oldHandlerMethod.getBean() + "' bean method\n" + oldHandlerMethod + " mapped.");
        }

        this.handlerMethods.put(mapping, newHandlerMethod);
        if (logger.isInfoEnabled()) {
            logger.info("Mapped \"" + mapping + "\" onto " + newHandlerMethod);
        }

        Set<String> patterns = getMappingPathPatterns(mapping);
        for (String pattern : patterns) {
            this.urlMap.put(pattern, mapping);
        }
    }

    private Set<String> getMappingPathPatterns(RequestMappingInfo mapping) {
        return mapping.getPatterns();
    }

    private HandlerMethod createHandlerMethod(String beanName, Method method) {
        return new HandlerMethod(beanName, method, getFactory());
    }


    /**
     * 使用中获取对应调用链的方法
     *
     * @param request
     * @return
     */
    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) {
        HandlerMethod handler = getHandlerInternal(request);
        return getHandlerExecutionChain(handler, request);
    }

    protected HandlerMethod getHandlerInternal(HttpServletRequest request) {
        String lookupPath = this.urlPathHelper.findPathWithinApplication(request);
        HandlerMethod handlerMethod = lookupHandlerMethod(lookupPath, request);

        return (handlerMethod != null) ? handlerMethod.createWithResolvedBean() : null;

    }


    protected HandlerMethod lookupHandlerMethod(String lookupPath, HttpServletRequest request) {
        RequestMappingInfo matchedRequestMapping = this.urlMap.get(lookupPath);
        if (matchedRequestMapping == null) {
            matchedRequestMapping = getMatchingMapping(this.handlerMethods.keySet(), lookupPath);
            if (matchedRequestMapping == null) {
                throw new IllegalStateException(
                        "No Such request: '" + request.getRequestURL() + "'");
            }
        }
        return this.handlerMethods.get(matchedRequestMapping);

    }

    private RequestMappingInfo getMatchingMapping(Set<RequestMappingInfo> mappings, String lookupPath) {
        for (RequestMappingInfo mapping : mappings) {
            if (mapping.getPatterns().contains(lookupPath))
                return mapping;
        }
        return null;
    }


}
