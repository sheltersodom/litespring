package org.litespring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.type.classreading.AnnotationMetadataReadingVisitor;
import org.litespring.core.type.classreading.ClassMetaDataReadingVisitor;
import org.objectweb.asm.ClassReader;


import java.io.IOException;

/**
 * @autor sheltersodom
 * @create 2021-02-10-18:31
 */
public class ClassReaderTest {
    @Test
    public void testGetClassMeteData() throws IOException {
        ClassReader reader = new ClassReader("org.litespring.service.v4.PetStoreService");
        ClassMetaDataReadingVisitor visitor = new ClassMetaDataReadingVisitor();
        reader.accept(visitor, ClassReader.SKIP_DEBUG);
        Assert.assertFalse(visitor.isAbstract());
        Assert.assertFalse(visitor.isInterface());
        Assert.assertFalse(visitor.isFinal());
        Assert.assertEquals("org.litespring.service.v4.PetStoreService", visitor.getClassName());
        Assert.assertEquals("java.lang.Object", visitor.getSuperClassName());
        Assert.assertEquals(0, visitor.getInterfaceNames().length);
    }

    @Test
    public void testGetAnnonation() throws IOException {
        ClassReader reader = new ClassReader("org.litespring.service.v4.PetStoreService");
        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        reader.accept(visitor, ClassReader.SKIP_DEBUG);
        String annotation = "org.litespring.stereotype.Component";

        Assert.assertTrue(visitor.hasAnnotation(annotation));
        AnnotationAttributes attribute = visitor.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore", attribute.get("value"));
    }
}
