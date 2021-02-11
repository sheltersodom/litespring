package org.litespring.context.annotation;

import org.litespring.beans.factory.annotation.AnnotatedBeanDefinition;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.core.type.AnnotationMetadata;

/**
 * @autor sheltersodom
 * @create 2021-02-10-23:48
 */
public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {
    private final AnnotationMetadata metadata;

    public ScannedGenericBeanDefinition(AnnotationMetadata metadata) {
        this.metadata = metadata;
        setBeanClassName(this.metadata.getClassName());
    }

    public final AnnotationMetadata getMetadata() {
        return metadata;
    }
}
