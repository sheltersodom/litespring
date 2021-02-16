package org.litespring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.litespring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * @autor sheltersodom
 * @create 2021-02-15-17:51
 */
public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice {

    public AspectJAfterThrowingAdvice(Method adviceMethod,
                                      AspectJExpressionPointcut pointcut,
                                      AspectInstanceFactory adviceObjectFactory) {
        super(adviceMethod, pointcut, adviceObjectFactory);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        try {
            return mi.proceed();
        } catch (Throwable throwable) {
            this.invokeAdviceMethod();
            throw throwable;
        }
    }
}
