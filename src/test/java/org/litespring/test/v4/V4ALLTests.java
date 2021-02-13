package org.litespring.test.v4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.litespring.test.v3.ApplicationContextTestV3;
import org.litespring.test.v3.BeanDefinitionTestV3;
import org.litespring.test.v3.ConstructorResolverTest;

/**
 * @autor sheltersodom
 * @create 2021-02-07-22:12
 */
@Suite.SuiteClasses({ApplicationContextTestV4.class,
        AutowiredAnnotationProcessorTest.class,
        ClassPathBeanDefinitionScannerTest.class,
        ClassReaderTest.class,
        DependencyDescriptorTest.class,
        InjectionMetadataTest.class,
        MetadataReaderTest.class,
        PackageResourceLoaderTest.class,
        XmlBeanDefinitionReaderTest.class,})
@RunWith(Suite.class)
public class V4ALLTests {
}
