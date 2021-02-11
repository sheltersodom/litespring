package org.litespring.stereotype;

import java.lang.annotation.*;

/**
 * @autor sheltersodom
 * @create 2021-02-10-16:35
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     *
     * @return the suggested component name, if any
     */
    String value() default "";
}
