package org.litespring.web.test.v1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.litespring.test.v1.ApplicationContextTest;
import org.litespring.test.v1.BeanFactoryTest;
import org.litespring.test.v1.ResourceTest;

/**
 * @autor sheltersodom
 * @create 2021-02-05-17:21
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        InterceptorTest.class,
        methodHandlerTest.class,
        RequestInfoTest.class})
public class V1ALLTests {
}
