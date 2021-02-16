package org.litespring.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.litespring.test.v1.V1ALLTests;
import org.litespring.test.v2.V2ALLTests;
import org.litespring.test.v3.V3ALLTests;
import org.litespring.test.v4.V4ALLTests;
import org.litespring.test.v5.V5ALLTests;

/**
 * @autor sheltersodom
 * @create 2021-02-07-22:15
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({V1ALLTests.class,
        V2ALLTests.class,
        V3ALLTests.class,
        V4ALLTests.class,
        V5ALLTests.class})
public class ALLTests {
}
