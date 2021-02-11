package org.litespring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.io.Resource;
import org.litespring.core.io.support.PackageResourceLoader;

/**
 * @autor sheltersodom
 * @create 2021-02-10-16:57
 */
public class PackageResourceLoaderTest {
    @Test
    public void testGetResources() {
        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources("org.litespring.dao.v4");
        Assert.assertEquals(resources.length, 2);
    }
}
