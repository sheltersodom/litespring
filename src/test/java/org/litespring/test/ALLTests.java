package org.litespring.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.litespring.test.v1.V1ALLTests;
import org.litespring.test.v2.V2ALLTests;

/**
 * @autor sheltersodom
 * @create 2021-02-07-22:15
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({V1ALLTests.class,
        V2ALLTests.class})
public class ALLTests {
}