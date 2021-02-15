package org.litespring.aop.framework;

import org.litespring.aop.Advice;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @autor sheltersodom
 * @create 2021-02-15-22:26
 */
public interface AopConfig {
    Class<?> getTargetClass();

    Object getTargetObject();

    boolean isProxyTargetClass();

    Class<?>[] getProxiedInterfaces();

    boolean isInterfaceProxied(Class<?> intf);

    List<Advice> getAdvices();

    List<Advice> getAdvices(Method method/*, Class<?> targetClass*/);

    void addAdvice(Advice advice);
}
