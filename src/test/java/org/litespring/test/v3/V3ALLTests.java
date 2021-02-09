package org.litespring.test.v3;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.litespring.test.v2.*;

/**
 * @autor sheltersodom
 * @create 2021-02-07-22:12
 */
@Suite.SuiteClasses({ApplicationContextTestV3.class,
        BeanDefinitionTestV3.class,
        ConstructorResolverTest.class})
@RunWith(Suite.class)
public class V3ALLTests {
}
