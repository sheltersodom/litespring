package org.litespring.web.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.MethodParameter;
import org.litespring.utils.ReflectionUtils;
import org.litespring.web.controller.v1.PetStoreController;
import org.litespring.web.method.HandlerMethod;
import org.litespring.web.servlet.mvc.method.RequestMappingInfo;
import org.litespring.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * @autor sheltersodom
 * @create 2021-04-16-21:35
 */
public class methodHandlerTest {
    @Test
    public void testInitmethodHandler() {
        Set<Method> declaredMethods = ReflectionUtils.getAllMethods(PetStoreController.class);
        RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping();

        for (Method method : declaredMethods) {
            RequestMappingInfo mapping = handlerMapping.getMappingForMethod(method);
            handlerMapping.registerHandlerMethod("petStore", method, mapping);
        }

        Map<String, RequestMappingInfo> urlMap = handlerMapping.getUrlMap();
        Assert.assertTrue(urlMap.size() == 3);

        RequestMappingInfo requestMappingInfo = urlMap.get("/login");
        Assert.assertTrue(requestMappingInfo.getPatterns().size() == 3);
        Assert.assertTrue(requestMappingInfo.getPatterns().contains("/login"));
        Assert.assertTrue(requestMappingInfo.getPatterns().contains("/logout"));
        Assert.assertTrue(requestMappingInfo.getPatterns().contains("/abc"));

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        Assert.assertTrue(handlerMethods.size() == 1);
    }

    @Test
    public void testGetHandler() {
        Set<Method> declaredMethods = ReflectionUtils.getAllMethods(PetStoreController.class);
        RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping();

        for (Method method : declaredMethods) {
            RequestMappingInfo mapping = handlerMapping.getMappingForMethod(method);
            handlerMapping.registerHandlerMethod("petStore", method, mapping);
        }
    }

    @Test
    public void methodHandlerComponent() throws NoSuchMethodException {
        HandlerMethod method = new HandlerMethod(null, PetStoreController.class.getDeclaredMethod("getIndex", int.class), null);
        MethodParameter[] methodParameters = method.getMethodParameters();
        Assert.assertTrue(methodParameters.length==1);
        Assert.assertTrue(methodParameters[0].getParameterName().equals("id"));
        MethodParameter returnType = method.getReturnType();
        Assert.assertTrue(returnType.getParameterType().isAssignableFrom(String.class));
    }
}
