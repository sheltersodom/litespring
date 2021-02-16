package org.litespring.test.v5;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.litespring.test.v4.*;

/**
 * @autor sheltersodom
 * @create 2021-02-07-22:12
 */
@Suite.SuiteClasses({ApplicationContextTestV5.class,
        BeanDefinitionTestV5.class,
        BeanFactoryTest.class,
        CglibAopProxyTest.class,
        CGLibTest.class,
        MethodLocatingFactoryTest.class,
        PointCutTest.class,
        ReflectiveMethodInvocationTest.class})
@RunWith(Suite.class)
public class V5ALLTests {
}
