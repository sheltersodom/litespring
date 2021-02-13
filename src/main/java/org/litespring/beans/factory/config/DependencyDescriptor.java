package org.litespring.beans.factory.config;

import org.litespring.utils.Assert;

import java.lang.reflect.Field;

/**
 * @autor sheltersodom
 * @create 2021-02-13-16:39
 */
public class DependencyDescriptor {
    private Field field;
    private boolean required;

    public DependencyDescriptor(Field field, boolean required) {
        Assert.notNull(field, "Filed must not be null");
        this.field = field;
        this.required = required;
    }

    public Class<?> getDependencyType() {
        if (this.field != null) {
            return field.getType();
        }
        throw new RuntimeException("only support field dependency");
    }

    public boolean isRequired() {
        return required;
    }
}
