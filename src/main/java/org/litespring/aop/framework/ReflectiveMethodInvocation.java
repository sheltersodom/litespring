package org.litespring.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @autor sheltersodom
 * @create 2021-02-14-23:47
 */
public class ReflectiveMethodInvocation implements MethodInvocation {
    protected final Object targetObject;
    protected final Method targetMethod;
    protected Object[] arguments;

    protected final List<MethodInterceptor> interceptors;
    private int currentInterceptorIndex = -1;


    public ReflectiveMethodInvocation(Object targetObject, Method targetMethod, Object[] arguments, List<MethodInterceptor> interceptors) {
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.arguments = arguments;
        this.interceptors = interceptors;
    }

    @Override
    public Method getMethod() {
        return targetMethod;
    }

    @Override
    public Object[] getArguments() {
        return arguments != null ? this.arguments : new Object[0];
    }

    /**
     * chain of responsibility
     * @return
     * @throws Throwable
     */
    @Override
    public Object proceed() throws Throwable {
        if (this.currentInterceptorIndex == this.interceptors.size() - 1) {
            return invokeJoinpoint();
        }
        this.currentInterceptorIndex++;
        MethodInterceptor interceptor = this.interceptors.get(this.currentInterceptorIndex);
        return interceptor.invoke(this);
    }

    private Object invokeJoinpoint() throws Throwable {
        return this.targetMethod.invoke(this.targetObject, arguments);
    }

    @Override
    public Object getThis() {
        return null;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return null;
    }
}
