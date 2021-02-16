package org.litespring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.litespring.aop.Advice;
import org.litespring.aop.Pointcut;
import org.litespring.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * @autor sheltersodom
 * @create 2021-02-15-17:44
 */
public abstract class AbstractAspectJAdvice implements Advice {
    private Method adviceMethod;
    private Pointcut pc;
    private AspectInstanceFactory adviceObjectFactory;

    public AbstractAspectJAdvice(Method adviceMethod,
                                 AspectJExpressionPointcut pointcut,
                                 AspectInstanceFactory adviceObjectFactory) {
        this.adviceMethod = adviceMethod;
        this.pc = pointcut;
        this.adviceObjectFactory = adviceObjectFactory;
    }

    @Override
    public Pointcut getPointcut() {
        return pc;
    }

    public void invokeAdviceMethod() throws Throwable {
        adviceMethod.invoke(adviceObjectFactory.getAspectInstance());
    }

    public abstract Object invoke(MethodInvocation mi) throws Throwable;

    public Method getAdviceMethod() {
        return adviceMethod;
    }

    public Object getAdviceInstance() {
        return adviceObjectFactory.getAspectInstance();
    }
}
