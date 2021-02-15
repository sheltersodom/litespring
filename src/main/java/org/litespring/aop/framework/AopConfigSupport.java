package org.litespring.aop.framework;

import org.litespring.aop.Advice;
import org.litespring.aop.Pointcut;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor sheltersodom
 * @create 2021-02-15-22:30
 */
public class AopConfigSupport implements AopConfig {
    private boolean proxyTargetClass = false;
    private Object targetObject = null;
    private List<Advice> advices = new ArrayList<>();
    private List<Class> interfaces = new ArrayList<>();

    public AopConfigSupport() {
    }

    public void setTargetObject(Object targetObject) {
        this.targetObject = targetObject;
    }

    @Override
    public Class<?> getTargetClass() {
        return this.targetObject.getClass();
    }

    @Override
    public Object getTargetObject() {
        return this.targetObject;
    }

    @Override
    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    @Override
    public Class<?>[] getProxiedInterfaces() {
        return this.interfaces.toArray(new Class[this.interfaces.size()]);
    }

    @Override
    public boolean isInterfaceProxied(Class<?> intf) {
        for (Class proxyIntf : this.interfaces) {
            if (intf.isAssignableFrom(proxyIntf)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addAdvice(Advice advice) {
        this.advices.add(advice);
    }

    @Override
    public List<Advice> getAdvices() {
        return this.advices;
    }

    @Override
    public List<Advice> getAdvices(Method method/*, Class<?> targetClass*/) {
        List<Advice> result = new ArrayList<>();
        for (Advice advice : this.getAdvices()) {
            Pointcut pc = advice.getPointcut();
            if (pc.getMethodMatcher().matches(method)) {
                result.add(advice);
            }
        }
        return result;
    }
}
