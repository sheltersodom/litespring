package org.litespring.stereotype;

import java.lang.annotation.*;

/**
 * @autor sheltersodom
 * @create 2021-03-30-11:44
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller {
    String value() default "";
}
