package org.litespring.test.v5;

import net.sf.cglib.proxy.*;
import org.junit.Test;
import org.litespring.service.v5.PetStoreService;
import org.litespring.tx.TransactionManager;

import java.lang.reflect.Method;

/**
 * @autor sheltersodom
 * @create 2021-02-15-18:54
 */
public class CGLibTest {
    @Test
    public void testCallBack() {
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(PetStoreService.class);
        enhancer.setCallback(new TransactionInterceptor());
        PetStoreService petStore = (PetStoreService) enhancer.create();
        petStore.placeOrder();
    }

    private class TransactionInterceptor implements MethodInterceptor {
        TransactionManager tx = new TransactionManager();

        @Override
        public Object intercept(Object obj, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
            tx.start();
            Object result = proxy.invokeSuper(obj, objects);
            tx.commit();
            return result;
        }
    }

    @Test
    public void testFilter() {
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(PetStoreService.class);
        enhancer.setInterceptDuringConstruction(false);

        Callback[] callbacks = new Callback[]{new TransactionInterceptor(), NoOp.INSTANCE};
        Class<?>[] types = new Class<?>[callbacks.length];
        for (int x = 0; x < types.length; x++) {
            types[x] = callbacks[x].getClass();
        }

        enhancer.setCallbackFilter(new ProxyCallbackFilter());
        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackTypes(types);

        PetStoreService petStore = (PetStoreService) enhancer.create();
        petStore.placeOrder();
    }

    private class ProxyCallbackFilter implements CallbackFilter {
        @Override
        public int accept(Method method) {
            if (method.getName().startsWith("place")) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
