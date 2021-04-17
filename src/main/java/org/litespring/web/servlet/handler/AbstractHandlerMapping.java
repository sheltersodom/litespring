package org.litespring.web.servlet.handler;

import org.litespring.beans.BeansException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.BeanFactoryAware;
import org.litespring.beans.factory.InitializingBean;
import org.litespring.utils.ClassUtils;
import org.litespring.web.method.HandlerMethod;
import org.litespring.web.servlet.HandlerExecutionChain;
import org.litespring.web.servlet.HandlerInterceptor;
import org.litespring.web.servlet.HandlerMapping;
import org.litespring.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @autor sheltersodom
 * @create 2021-04-01-17:33
 */
public abstract class AbstractHandlerMapping implements HandlerMapping, InitializingBean, BeanFactoryAware {
    protected UrlPathHelper urlPathHelper = new UrlPathHelper();
    //    private final List<HandlerInterceptor> adaptedInterceptors = new ArrayList<>();
    private final List<MappedInterceptor> mappedInterceptors = new ArrayList<>();
    private BeanFactory factory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.factory = beanFactory;
    }

    public BeanFactory getFactory() {
        return factory;
    }

    public HandlerExecutionChain getHandlerExecutionChain(HandlerMethod handler, HttpServletRequest request) {
        HandlerExecutionChain chain = new HandlerExecutionChain(handler);
//        chain.addInterceptors(adaptedInterceptors);

        String lookupPath = urlPathHelper.findPathWithinApplication(request);
        for (MappedInterceptor mappedInterceptor : mappedInterceptors) {
            if (mappedInterceptor.matches(lookupPath)) {
                chain.addInterceptor(mappedInterceptor.getInterceptor());
            }
        }
        return chain;
    }

    @Override
    public void afterProperties() {
        detectMappedInterceptors();
        initHandlerMethods();
    }

    private void detectMappedInterceptors() {
        Class<?> clazz = MappedInterceptor.class;
        Set<Class> interfaces = ClassUtils.getAllInterfacesForClassAsSet(clazz);
        interfaces.add(clazz);
        List<Object> interceptorBeans = new ArrayList<>();
        for (Class inf : interfaces) {
            interceptorBeans.addAll(getFactory().getBeansByType(inf));
        }

        for (Object interceptorBean : interceptorBeans) {
            mappedInterceptors.add((MappedInterceptor) interceptorBean);
        }
    }

    protected abstract void initHandlerMethods();
}
