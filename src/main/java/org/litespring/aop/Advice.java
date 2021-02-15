package org.litespring.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @autor sheltersodom
 * @create 2021-02-14-23:36
 */
public interface Advice extends MethodInterceptor {
    Pointcut getPointcut();
}
