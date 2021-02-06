package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * @autor sheltersodom
 * @create 2021-02-05-17:24
 */
public class ResourceTest {
    @Test
    public void testClassPathResource() {
        try {
            Resource r = new ClassPathResource("petstore-v1");
            InputStream is = r.getInputStream();
            //准备不充分,应当充分比较
            Assert.assertNotNull(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void testFileSystemResource() {
//        try {
//            Resource r = new FileSystemResource("D:\\Code\\litespring\\src\\test\\resources\\petstore-v1");
//            InputStream is = r.getInputStream();
//            Assert.assertNotNull(is);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
