package org.litespring.utils;

/**
 * @autor sheltersodom
 * @create 2021-02-05-17:41
 */
public class Assert {
    public static void notNull(Object object, String message) {
        if (object == null) throw new IllegalArgumentException(message);
    }
}
