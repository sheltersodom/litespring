package org.litespring.beans.factory.annotation;

import java.util.List;

/**
 * @autor sheltersodom
 * @create 2021-02-13-18:08
 */
public class InjectionMetadata {
    private final Class<?> targetClass;
    List<InjectionElement> injectionElements;

    public InjectionMetadata(Class<?> targetClass, List<InjectionElement> injectionElements) {
        this.targetClass = targetClass;
        this.injectionElements = injectionElements;
    }

    public List<InjectionElement> getInjectionElements() {
        return injectionElements;
    }

    public void inject(Object target) {
        if (injectionElements == null || injectionElements.isEmpty()) {
            return;
        }
        for (InjectionElement ele : injectionElements) {
            ele.inject(target);
        }
    }
}
