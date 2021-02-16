package org.litespring.test.v5;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.aop.aspectj.AspectJAfterReturningAdvice;
import org.litespring.aop.aspectj.AspectJAfterThrowingAdvice;
import org.litespring.aop.aspectj.AspectJBeforeAdvice;
import org.litespring.aop.config.AspectInstanceFactory;
import org.litespring.aop.framework.ReflectiveMethodInvocation;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.service.v5.PetStoreService;
import org.litespring.tx.TransactionManager;
import org.litespring.util.MessageTracker;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor sheltersodom
 * @create 2021-02-14-23:22
 */
public class ReflectiveMethodInvocationTest extends AbstractV5Test {
    private AspectJBeforeAdvice beforeAdvice = null;
    private AspectJAfterReturningAdvice afterAdvice = null;
    private AspectJAfterThrowingAdvice afterThrowingAdvice = null;
    private PetStoreService petStoreService = null;
    private BeanFactory beanFactory = null;
    private AspectInstanceFactory aspectInstanceFactory = null;

    @Before
    public void setup() throws NoSuchMethodException {
        petStoreService = new PetStoreService();
        MessageTracker.clearMsgs();

        beanFactory=this.getBeanFactory("petstore-v5");
        aspectInstanceFactory=this.getAspectInstanceFactory("tx");
        aspectInstanceFactory.setBeanFactory(beanFactory);

        beforeAdvice = new AspectJBeforeAdvice(TransactionManager.class.getMethod("start"), null, aspectInstanceFactory);
        afterAdvice = new AspectJAfterReturningAdvice(TransactionManager.class.getMethod("commit"), null, aspectInstanceFactory);
        afterThrowingAdvice = new AspectJAfterThrowingAdvice(TransactionManager.class.getMethod("rollback"), null, aspectInstanceFactory);

    }

    @Test
    public void testMethodInvocation() throws Throwable {
        Method targetMethod = PetStoreService.class.getMethod("placeOrder");
        List<MethodInterceptor> interceptors = new ArrayList<>();
        interceptors.add(beforeAdvice);
        interceptors.add(afterAdvice);

        ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(petStoreService, targetMethod, new Object[0], interceptors);
        mi.proceed();

        List<String> msgs = MessageTracker.getMsgs();
        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));
    }
    @Test
    public void testAfterThrowing() throws Throwable {
        Method targetMethod = PetStoreService.class.getMethod("placeOrderWithException");
        List<MethodInterceptor> interceptors = new ArrayList<>();
        interceptors.add(beforeAdvice);
        interceptors.add(afterThrowingAdvice);
        interceptors.add(afterAdvice);

        ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(petStoreService, targetMethod, new Object[0], interceptors);
        try {
            mi.proceed();
        } catch (Throwable throwable) {
            List<String> msgs = MessageTracker.getMsgs();
            Assert.assertEquals(2, msgs.size());
            Assert.assertEquals("start tx", msgs.get(0));
            Assert.assertEquals("rollback tx", msgs.get(1));
            return;
        }
        Assert.fail("No Exception throwed");
    }
}
