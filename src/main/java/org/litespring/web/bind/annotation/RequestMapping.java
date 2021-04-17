package org.litespring.web.bind.annotation;

import java.lang.annotation.*;

/**
 * @autor sheltersodom
 * @create 2021-04-02-15:45
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String[] value() default {};
}
