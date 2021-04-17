package org.litespring.web.test.v2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.litespring.web.test.v1.InterceptorTest;
import org.litespring.web.test.v1.RequestInfoTest;
import org.litespring.web.test.v1.methodHandlerTest;

/**
 * @autor sheltersodom
 * @create 2021-02-05-17:21
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        HandlerMethodsTest.class,
        ScannerTest.class})
public class V2ALLTests {
}
