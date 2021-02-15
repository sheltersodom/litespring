package org.litespring.aop.framework;

import net.sf.cglib.proxy.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.aop.Advice;
import org.litespring.utils.Assert;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor sheltersodom
 * @create 2021-02-15-22:41
 */
@SuppressWarnings("serial")
public class CglibProxyFactory implements AopProxyFactory {
    // Constants for CGLIB callback array indices
    private static final int AOP_PROXY = 0;
    private static final int INVOKE_TARGET = 1;
    private static final int NO_OVERRIDE = 2;
    private static final int DISPATCH_TARGET = 3;
    private static final int DISPATCH_ADVISED = 4;
    private static final int INVOKE_EQUALS = 5;
    private static final int INVOKE_HASHCODE = 6;


    protected static final Log logger = LogFactory.getLog(CglibProxyFactory.class);

    protected final AopConfig config;

    private Object[] constructorArgs;
    private Class<?>[] constructorArgTypes;

    public CglibProxyFactory(AopConfig config) {
        Assert.notNull(config, "AdvisedSupport must not be null");
        if (config.getAdvices().size() == 0/*&& config.getTargetSource() == AdvisedSupport.EMPTY_TARGET_SOURCE*/) {
            throw new AopConfigException("No advisors and no TargetSource specified");
        }
        this.config = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(null);
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        if (logger.isDebugEnabled()) {
            logger.debug("Creating CGLIB proxy: target source is " + this.config.getTargetClass());
        }

        Class<?> rootClass = this.config.getTargetClass();

        Enhancer enhancer = new Enhancer();

        if (classLoader != null) {
            enhancer.setClassLoader(classLoader);
        }
        enhancer.setSuperclass(rootClass);

//        enhancer.setNamingPolicy("liteSpringCGLIB");//"BySpringCGLIB"
        //标志位
        enhancer.setInterceptDuringConstruction(false);

        Callback[] callbacks = getCallbacks(rootClass);
        Class<?>[] types = new Class<?>[callbacks.length];
        for (int x = 0; x < types.length; x++) {
            types[x] = callbacks[x].getClass();
        }

        enhancer.setCallbackFilter(new ProxyCallbackFilter(this.config));
        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackTypes(types);

        Object proxy;
        if (this.constructorArgs != null) {
            proxy = enhancer.create(this.constructorArgTypes, this.constructorArgs);
        } else {
            proxy = enhancer.create();
        }
        return proxy;
    }

    private Callback[] getCallbacks(Class<?> rootClass) {
        Callback aopInterceptor = new DynamicAdvisedInterceptor(this.config);

        //Callback targetInterceptor = new StaticUnadvisedExposedInterceptor(this.advised.getTargetObject());

        //Callback targetDispatcher = new StaticDispatcher(this.advised.getTargetObject());

        Callback[] callbacks = new Callback[]{
                aopInterceptor,  // AOP_PROXY for normal advice
                /*targetInterceptor,  // INVOKE_TARGET invoke target without considering advice, if optimized
                new SerializableNoOp(),  // NO_OVERRIDE  no override for methods mapped to this
                targetDispatcher,        //DISPATCH_TARGET
                this.advisedDispatcher,  //DISPATCH_ADVISED
                new EqualsInterceptor(this.advised),
                new HashCodeInterceptor(this.advised)*/
        };

        return callbacks;
    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor, Serializable {
        private final AopConfig config;

        public DynamicAdvisedInterceptor(AopConfig config) {
            this.config = config;
        }

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            Object target = null;
            target = this.config.getTargetObject();

            List<Advice> chain = this.config.getAdvices(method);
            Object retVal;
            // Check whether we only have one InvokerInterceptor: that is,
            // no real advice, but just reflective invocation of the target.
            if (chain.isEmpty() && Modifier.isPublic(method.getModifiers())) {
                // We can skip creating a MethodInvocation: just invoke the target directly.
                // Note that the final invoker must be an InvokerInterceptor, so we know
                // it does nothing but a reflective operation on the target, and no hot
                // swapping or fancy proxying.
                //proxy可以调用两个参数,一个是父类的方法,一个是子类的方法,区别在于第一个参数
                retVal = proxy.invoke(target, args);
            } else {
                List<org.aopalliance.intercept.MethodInterceptor> interceptors = new ArrayList<>();
                interceptors.addAll(chain);
                // We need to create a method invocation...
                ReflectiveMethodInvocation mi = new ReflectiveMethodInvocation(target, method, args, interceptors);
                retVal = mi.proceed();
            }
            return retVal;
        }
    }

    private class ProxyCallbackFilter implements CallbackFilter {
        private final AopConfig config;

        public ProxyCallbackFilter(AopConfig config) {
            this.config = config;
        }

        @Override
        public int accept(Method method) {
            //永远返回第一个
            return AOP_PROXY;
        }
    }


}
