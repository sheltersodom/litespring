package org.litespring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.litespring.aop.Advice;
import org.litespring.aop.Pointcut;
import org.litespring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * @autor sheltersodom
 * @create 2021-02-14-23:38
 */
public class AspectJBeforeAdvice extends AbstractAspectJAdvice {

    public AspectJBeforeAdvice(Method adviceMethod,
                               AspectJExpressionPointcut pointcut,
                               AspectInstanceFactory adviceObjectFactory) {
        super(adviceMethod, pointcut, adviceObjectFactory);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        //相当于调用逻辑链中的前置方法
        this.invokeAdviceMethod();
        Object o = mi.proceed();
        return o;
    }
}
