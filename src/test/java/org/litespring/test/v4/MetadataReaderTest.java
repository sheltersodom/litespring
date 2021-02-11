package org.litespring.test.v4;


import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.core.type.classreading.MetadataReader;
import org.litespring.core.type.classreading.SimpleMetadataReader;
import org.litespring.stereotype.Component;

import java.io.IOException;

/**
 * @autor sheltersodom
 * @create 2021-02-10-23:07
 */
public class MetadataReaderTest {
    @Test
    public void testGetMetadata() throws IOException {
        MetadataReader reader=new SimpleMetadataReader("org.litespring.service.v4.PetStoreService");
        AnnotationMetadata amd=reader.getAnnotationMetadata();
        String annotation = Component.class.getName();
        Assert.assertTrue(amd.hasAnnotation(annotation));
        AnnotationAttributes attribute=amd.getAnnotationAttributes(annotation);
        Assert.assertEquals("petStore", attribute.get("value"));

        Assert.assertFalse(amd.isAbstract());
        Assert.assertFalse(amd.isInterface());
        Assert.assertFalse(amd.isFinal());
        Assert.assertEquals("org.litespring.service.v4.PetStoreService", amd.getClassName());
        Assert.assertEquals("java.lang.Object", amd.getSuperClassName());
        Assert.assertEquals(0, amd.getInterfaceNames().length);
    }
}
