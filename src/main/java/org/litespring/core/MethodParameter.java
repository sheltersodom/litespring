package org.litespring.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

/**
 * @autor sheltersodom
 * @create 2021-03-30-21:15
 */
public class MethodParameter {
    private final Method method;

    /*private final Constructor<?> constructor;*/

    private final int parameterIndex;

    /* private Class<?> containingClass;*/

    private Class<?> parameterType;
    //泛型参数
    private Type genericParameterType;
    //参数注解
    private Annotation[] parameterAnnotations;

    private String parameterName;

    public MethodParameter(Method method, int parameterIndex) {
        if (method == null) {
            throw new RuntimeException("please have a method Object");
        }
        this.method = method;
        this.parameterIndex = parameterIndex;
    }

    public Method getMethod() {
        return method;
    }

    public Annotation[] getParameterAnnotations() {
        if (this.parameterAnnotations == null) {
            Annotation[][] annotationArray = this.method.getParameterAnnotations();
            //是否是形参列表
            if (this.parameterIndex >= 0 && this.parameterIndex < annotationArray.length) {
                this.parameterAnnotations = annotationArray[this.parameterIndex];
            } else {
                this.parameterAnnotations = new Annotation[0];
            }
        }
        return this.parameterAnnotations;
    }

    public <T extends Annotation> boolean hasParameterAnnotation(Class<T> annotationType) {
        return (getParameterAnnotation(annotationType) != null);
    }

    public <T extends Annotation> T getParameterAnnotation(Class<T> annotationType) {
        Annotation[] anns = getParameterAnnotations();
        for (Annotation ann : anns) {
            if (annotationType.isInstance(ann)) {
                return (T) ann;
            }
        }
        return null;
    }

    public boolean hasParameterAnnotations() {
        return (getParameterAnnotations().length != 0);
    }

    public Class<?> getParameterType() {
        if (this.parameterType == null) {
            if (parameterIndex < 0) {
                this.parameterType = this.method.getReturnType();
            } else {
                this.parameterType = this.method.getParameterTypes()[this.parameterIndex];
            }
        }
        return this.parameterType;
    }

    public String getParameterName() {
        if (parameterName == null) {
            Parameter[] parameters = this.method.getParameters();
            this.parameterName = parameters[this.parameterIndex].getName();
        }

        return this.parameterName;
    }

    public Type getGenericParameterType() {
        if (this.genericParameterType == null) {
            if (this.parameterIndex < 0) {
                this.genericParameterType = this.method.getGenericReturnType();
            } else {
                this.genericParameterType = this.method.getGenericParameterTypes()[this.parameterIndex];
            }
        }
        return this.genericParameterType;
    }
}
