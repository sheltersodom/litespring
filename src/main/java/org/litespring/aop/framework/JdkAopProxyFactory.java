package org.litespring.aop.framework;


import org.aopalliance.intercept.MethodInterceptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.aop.Advice;
import org.litespring.utils.Assert;
import org.litespring.utils.ClassUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor sheltersodom
 * @create 2021-02-17-16:46
 */
public class JdkAopProxyFactory implements AopProxyFactory, InvocationHandler {
    private static final Log logger = LogFactory.getLog(CglibProxyFactory.class);

    private final AopConfig config;

    public JdkAopProxyFactory(AopConfig config) {
        Assert.notNull(config, "AdvisedSupport must not be null");
        if (config.getAdvices().size() == 0) {
            throw new AopConfigException("No advices specified");
        }
        this.config = config;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object target = this.config.getTargetObject();

        Object retVal;
        List<Advice> advices = this.config.getAdvices(method);
        if (advices.isEmpty()) {
            retVal = method.invoke(target, args);
        } else {
            List<MethodInterceptor> interceptors = new ArrayList<>();
            interceptors.addAll(advices);
            ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(target, method, args, interceptors);
            retVal = mi.proceed();
        }
        return retVal;
    }

    @Override
    public Object getProxy() {
        return getProxy(ClassUtils.getDefaultClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        if (logger.isDebugEnabled()) {
            logger.debug("Creating JDK dynamic proxy:target source is" + this.config.getTargetClass());
        }
        Class<?>[] proxiedInterfaces = config.getProxiedInterfaces();
        return Proxy.newProxyInstance(classLoader, proxiedInterfaces, this);
    }
}
