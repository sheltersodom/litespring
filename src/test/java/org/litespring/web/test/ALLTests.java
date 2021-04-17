package org.litespring.web.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.litespring.web.test.v1.V1ALLTests;
import org.litespring.web.test.v2.V2ALLTests;
import org.litespring.web.test.v3.V3ALLTests;


/**
 * @autor sheltersodom
 * @create 2021-02-07-22:15
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({V1ALLTests.class,
        V2ALLTests.class,
        V3ALLTests.class})
public class ALLTests {
}
