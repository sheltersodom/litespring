package org.litespring.test.v5;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.aop.aspectj.AspectJAfterReturningAdvice;
import org.litespring.aop.aspectj.AspectJBeforeAdvice;
import org.litespring.aop.aspectj.AspectJExpressionPointcut;
import org.litespring.aop.framework.AopConfig;
import org.litespring.aop.framework.AopConfigSupport;
import org.litespring.aop.framework.CglibProxyFactory;
import org.litespring.service.v5.PetStoreService;
import org.litespring.tx.TransactionManager;
import org.litespring.util.MessageTracker;

import java.util.List;

/**
 * @autor sheltersodom
 * @create 2021-02-15-19:26
 */
public class CglibAopProxyTest {
    private AspectJBeforeAdvice beforeAdvice = null;
    private AspectJAfterReturningAdvice afterAdvice = null;
    private AspectJExpressionPointcut pc = null;
    private PetStoreService petStoreService = null;
    private TransactionManager tx;

    @Before
    public void setup() throws NoSuchMethodException {
        petStoreService = new PetStoreService();
        tx = new TransactionManager();
        String expression = "execution(* org.litespring.service.v5.*.placeOrder(..))";
        pc = new AspectJExpressionPointcut();
        pc.setExpression(expression);
        MessageTracker.clearMsgs();
        beforeAdvice = new AspectJBeforeAdvice(TransactionManager.class.getMethod("start"), pc, tx);
        afterAdvice = new AspectJAfterReturningAdvice(TransactionManager.class.getMethod("commit"), pc, tx);

    }

    @Test
    public void testGetProxy() {
        AopConfig config = new AopConfigSupport();
        config.addAdvice(beforeAdvice);
        config.addAdvice(afterAdvice);
        ((AopConfigSupport) config).setTargetObject(new PetStoreService());

        CglibProxyFactory proxyFactory = new CglibProxyFactory(config);
        PetStoreService proxy = (PetStoreService) proxyFactory.getProxy();
        proxy.placeOrder();

        List<String> msgs = MessageTracker.getMsgs();
        Assert.assertEquals(3, msgs.size());
        Assert.assertEquals("start tx", msgs.get(0));
        Assert.assertEquals("place order", msgs.get(1));
        Assert.assertEquals("commit tx", msgs.get(2));
    }
}
