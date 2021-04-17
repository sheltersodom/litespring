package org.litespring.web.test.v3;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.web.servlet.View;
import org.litespring.web.servlet.view.InternalResourceView;
import org.litespring.web.servlet.view.InternalResourceViewResolver;
import org.litespring.web.servlet.view.RedirectView;
import testController.PetStoreController;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;

/**
 * @autor sheltersodom
 * @create 2021-04-16-21:57
 */
public class testViewResolver {
    @Test
    public void testViewResolver(){
        InternalResourceViewResolver resolver=new InternalResourceViewResolver();
        {
            View view = resolver.resolveViewName("redirect:/index");
            Assert.assertTrue(view instanceof RedirectView);
            RedirectView redirectView = (RedirectView) view;
            Assert.assertTrue(redirectView.getUrl().equals("/index"));
        }
        {
            View view = resolver.resolveViewName("/abc");
            Assert.assertTrue(view instanceof InternalResourceView);
            InternalResourceView redirectView = (InternalResourceView) view;
            Assert.assertTrue(redirectView.getUrl().equals("/abc"));
        }

    }

    @Test
    public void main() throws NoSuchMethodException {
        Method method = PetStoreController.class.getDeclaredMethod("getIndex", int.class);
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            System.out.println(parameter.getName());
        }

    }
}
