package org.litespring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.litespring.aop.Advice;
import org.litespring.aop.Pointcut;
import org.litespring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * @autor sheltersodom
 * @create 2021-02-14-23:45
 */
public class AspectJAfterReturningAdvice extends AbstractAspectJAdvice {

    public AspectJAfterReturningAdvice(Method adviceMethod,
                                       AspectJExpressionPointcut pointcut,
                                       AspectInstanceFactory adviceObjectFactory) {
        super(adviceMethod, pointcut, adviceObjectFactory);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        Object o = mi.proceed();
        //相当于调用逻辑链中的后置方法
        this.invokeAdviceMethod();
        return o;
    }
}
