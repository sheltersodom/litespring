package org.litespring.stereotype;

/**
 * @autor sheltersodom
 * @create 2021-03-30-11:44
 */
@Component
public @interface Service {
    String value() default "";
}
