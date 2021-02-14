package org.litespring.aop;

/**
 * @autor sheltersodom
 * @create 2021-02-14-19:41
 */
public interface Pointcut {
    MethodMatcher getMethodMatcher();

    String getExpression();
}
