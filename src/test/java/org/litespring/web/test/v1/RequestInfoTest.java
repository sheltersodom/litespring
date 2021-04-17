package org.litespring.web.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.web.bind.annotation.RequestMapping;
import org.litespring.web.controller.v1.PetStoreController;
import org.litespring.web.servlet.mvc.method.RequestMappingInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @autor sheltersodom
 * @create 2021-04-15-21:41
 */
public class RequestInfoTest {
    @Test
    public void RequestInfoTest() throws NoSuchMethodException {
        Method method = PetStoreController.class.getDeclaredMethod("getIndex", int.class);
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        RequestMappingInfo requestMappingInfo = new RequestMappingInfo(annotation.value());
        Assert.assertTrue(requestMappingInfo.getPatterns().size() == 3);
        Assert.assertTrue(requestMappingInfo.getPatterns().contains("/login"));
        Assert.assertTrue(requestMappingInfo.getPatterns().contains("/logout"));
        Assert.assertTrue(requestMappingInfo.getPatterns().contains("/abc"));
    }
}
