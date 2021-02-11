package org.litespring.core.type.classreading;

import org.litespring.core.annotation.AnnotationAttributes;
import org.objectweb.asm.AnnotationVisitor;

import java.util.Map;

import static org.objectweb.asm.Opcodes.ASM4;

/**
 * @autor sheltersodom
 * @create 2021-02-10-22:29
 */
public class AnnotationAttributesReadingVisitor extends AnnotationVisitor {
    private final String annotationType;
    private final Map<String, AnnotationAttributes> attributeMap;
    AnnotationAttributes attributes = new AnnotationAttributes();

    public AnnotationAttributesReadingVisitor(String annotationType, Map<String, AnnotationAttributes> attributeMap) {
        super(ASM4);
        this.annotationType = annotationType;
        this.attributeMap = attributeMap;
    }

    @Override
    public void visitEnd() {
        this.attributeMap.put(this.annotationType, this.attributes);
    }

    public void visit(String attributeName, Object attributeValue) {
        this.attributes.put(attributeName, attributeValue);
    }
}
