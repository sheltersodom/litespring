package org.litespring.test.v2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @autor sheltersodom
 * @create 2021-02-07-22:12
 */
@Suite.SuiteClasses({ApplicationContextTestV2.class,
        BeanDefinitionTestV2.class,
        BeanDefinitionValueResloverTest.class,
        CustomerNumberEditorTest.class,
        CustomerBooleanEditorTest.class,
        TypeConverterTest.class})
@RunWith(Suite.class)
public class V2ALLTests {
}
