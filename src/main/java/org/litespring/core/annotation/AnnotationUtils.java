package org.litespring.core.annotation;

import org.litespring.stereotype.Controller;
import org.litespring.utils.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @autor sheltersodom
 * @create 2021-02-13-21:49
 */
public abstract class AnnotationUtils {
    /**
     * weakhashmap 通过虚引用实现发现及回收,减缓程序的运行垃圾
     */
    private static final Map<Class<?>, Boolean> annotatedInterfaceCache = new WeakHashMap<Class<?>, Boolean>();

    public static <T extends Annotation> T getAnnotation(AnnotatedElement ae, Class<T> annotationType) {
        T ann = ae.getAnnotation(annotationType);
        if (ann == null) {
            for (Annotation metaAnn : ae.getAnnotations()) {
                ann = metaAnn.annotationType().getAnnotation(annotationType);
                if (ann != null) {
                    break;
                }
            }
        }
        return ann;
    }

    public static <A extends Annotation> A findAnnotation(Method method, Class<A> annotationType) {
        A annotation = getAnnotation(method, annotationType);
        Class<?> clazz = method.getDeclaringClass();
        if (annotation == null) {
            annotation = searchOnInterfaces(method, annotationType, clazz.getInterfaces());
        }
        while (annotation == null) {
            clazz = clazz.getSuperclass();
            if (clazz == null || clazz.equals(Object.class)) {
                break;
            }
            try {
                Method equivalentMethod = clazz.getDeclaredMethod(method.getName(), method.getParameterTypes());
                annotation = getAnnotation(equivalentMethod, annotationType);
            } catch (NoSuchMethodException ex) {
                // No equivalent method found
            }
            if (annotation == null) {
                annotation = searchOnInterfaces(method, annotationType, clazz.getInterfaces());
            }
        }
        return annotation;
    }

    private static <A extends Annotation> A searchOnInterfaces(Method method, Class<A> annotationType, Class<?>[] ifcs) {
        A annotation = null;
        for (Class<?> iface : ifcs) {
            if (isInterfaceWithAnnotatedMethods(iface)) {
                try {
                    Method equivalentMethod = iface.getMethod(method.getName(), method.getParameterTypes());
                    annotation = getAnnotation(equivalentMethod, annotationType);
                } catch (NoSuchMethodException ex) {
                    // Skip this interface - it doesn't have the method...
                }
                if (annotation != null) {
                    break;
                }
            }
        }
        return annotation;
    }

    private static boolean isInterfaceWithAnnotatedMethods(Class<?> iface) {
        Boolean flag = annotatedInterfaceCache.get(iface);
        if (flag != null) {
            return flag;
        }
        boolean found = false;
        for (Method ifcMethod : iface.getMethods()) {
            if (ifcMethod.getAnnotations().length > 0) {
                found = true;
                break;
            }
        }
        annotatedInterfaceCache.put(iface, found);
        return found;

    }

    public static <A extends Annotation> A findAnnotation(Class<?> clazz, Class<A> annotationType) {
        Assert.notNull(clazz, "Class must not be null");
        A annotation = clazz.getAnnotation(annotationType);
        if (annotation != null) {
            return annotation;
        }
        for (Class<?> ifc : clazz.getInterfaces()) {
            annotation = findAnnotation(ifc, annotationType);
            if (annotation != null) {
                return annotation;
            }
        }
        if (!Annotation.class.isAssignableFrom(clazz)) {
            for (Annotation ann : clazz.getAnnotations()) {
                annotation = findAnnotation(ann.annotationType(), annotationType);
                if (annotation != null) {
                    return annotation;
                }
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass == null || superClass.equals(Object.class)) {
            return null;
        }
        return findAnnotation(superClass, annotationType);
    }
}
