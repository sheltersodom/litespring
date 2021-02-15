package org.litespring.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import org.litespring.aop.Advice;
import org.litespring.aop.Pointcut;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @autor sheltersodom
 * @create 2021-02-15-17:44
 */
public abstract class AbstractAspectJAdvice implements Advice {
    private Method adviceMethod;
    private Pointcut pc;
    private Object adviceObject;

    public AbstractAspectJAdvice(Method adviceMethod, AspectJExpressionPointcut pointcut, Object adviceObject) {
        this.adviceMethod = adviceMethod;
        this.pc = pointcut;
        this.adviceObject = adviceObject;
    }

    @Override
    public Pointcut getPointcut() {
        return pc;
    }

    public void invokeAdviceMethod() throws Throwable {
        adviceMethod.invoke(adviceObject);
    }

    public abstract Object invoke(MethodInvocation mi) throws Throwable;
}
