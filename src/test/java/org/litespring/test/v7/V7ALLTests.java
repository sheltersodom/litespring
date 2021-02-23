package org.litespring.test.v7;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.litespring.test.v6.ApplicationContextTestV6;

/**
 * @autor sheltersodom
 * @create 2021-02-07-22:12
 */
@Suite.SuiteClasses({ApplicationContextTestV7.class,
        ApplicationScannedContextTestV7.class,
        AopTestV7.class
       })
@RunWith(Suite.class)
public class V7ALLTests {
}
