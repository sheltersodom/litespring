package org.litespring.web.method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.core.MethodParameter;

import java.lang.reflect.Method;

/**
 * @autor sheltersodom
 * @create 2021-03-30-21:08
 */
public class HandlerMethod {
    protected final Log logger = LogFactory.getLog(HandlerMethod.class);

    private Object bean;

    private final String beanID;

    private final Method method;

    private final BeanFactory beanFactory;

    private final MethodParameter[] parameters;

    private final MethodParameter returnParameter;

    public HandlerMethod(String beanID, Method method, BeanFactory beanFactory) {
        this.beanID = beanID;
        this.method = method;
        this.beanFactory = beanFactory;
        parameters = initMethodParameters();
        returnParameter = initMethodReturnParameter();
    }

    private MethodParameter initMethodReturnParameter() {
       return new MethodParameter(method, -1);
    }

//    public HandlerMethod(Object bean, Method method, BeanFactory beanFactory) {
//        this.bean = bean;
//        this.beanID = bean.getClass().getName();
//        this.method = method;
//        this.beanFactory = beanFactory;
//        parameters = initMethodParameters();
//    }

    private MethodParameter[] initMethodParameters() {
        int length = this.method.getParameterTypes().length;
        MethodParameter[] result = new MethodParameter[length];
        for (int i = 0; i < length; i++) {
            result[i] = new MethodParameter(method, i);
        }
        return result;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Object getBean() {
        return bean;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getBeanType() {
        return this.bean.getClass();
    }

    public MethodParameter[] getMethodParameters() {
        return this.parameters;
    }

    public MethodParameter getReturnType() {
        return new MethodParameter(method, -1);
    }

    public boolean isVoid() {
        return Void.TYPE.equals(getReturnType().getParameterType());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj instanceof HandlerMethod) {
            HandlerMethod other = (HandlerMethod) obj;
            return this.bean.equals(other.bean) && this.method.equals(other.method);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.bean.hashCode() * 31 + this.method.hashCode();
    }

    @Override
    public String toString() {
        return this.method.toGenericString();
    }

    public HandlerMethod createWithResolvedBean() {
        if (bean == null) {
            bean = beanFactory.getBean(beanID);
            if (bean == null) {
                throw new IllegalStateException("No Such bean: '" + beanID + "'in the beanfactory");
            }
        }
        return this;
    }
}
