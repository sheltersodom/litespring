package org.litespring.core.type.classreading;

import org.litespring.core.type.AnnotationMetadata;
import org.litespring.core.type.ClassMetadata;
import org.objectweb.asm.ClassReader;

import java.io.IOException;

/**
 * @autor sheltersodom
 * @create 2021-02-10-23:23
 */
public class SimpleMetadataReader implements MetadataReader {

    private final ClassMetadata classMetadata;

    private final AnnotationMetadata annotationMetadata;

    public SimpleMetadataReader(String classPath) throws IOException {
        ClassReader classReader = new ClassReader(classPath);
        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        classReader.accept(visitor, ClassReader.SKIP_DEBUG);

        this.annotationMetadata = visitor;
        this.classMetadata = visitor;
    }

//    @Override
//    public Resource getResource() {
//        return null;
//    }

    @Override
    public ClassMetadata getClassMetadata() {
        return this.classMetadata;
    }

    @Override
    public AnnotationMetadata getAnnotationMetadata() {
        return this.annotationMetadata;
    }
}
