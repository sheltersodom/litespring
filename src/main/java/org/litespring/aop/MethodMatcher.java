package org.litespring.aop;

import java.lang.reflect.Method;

/**
 * @autor sheltersodom
 * @create 2021-02-14-19:40
 */
public interface MethodMatcher {
    boolean matches(Method method/* ,Class<?> targetClass */);
}
