package org.litespring.beans.factory.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.TypeMismatchException;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * @autor sheltersodom
 * @create 2021-02-08-23:35
 */
public class ConstructorResolver {
    protected final Log logger = LogFactory.getLog(getClass());

    private final AbstractBeanFactory beanFactory;

    public ConstructorResolver(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object autowireConstructor(BeanDefinition bd) {
        Constructor<?> constructorToUse = null;
        Object[] argsToUse = null;
        Class<?> beanClass = null;
        try {
            beanClass = bd.resolve(beanFactory.getBeanClassLoader());
        } catch (ClassNotFoundException e) {
            throw new BeanCreationException(bd.getID(), "Instantiation of bean failed, can't resolve class");
        }
        Constructor<?>[] candidates = beanClass.getConstructors();

        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this.beanFactory);

        ConstructorArgument cargs = bd.getConstructorArgument();
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();

        for (int i = 0; i < candidates.length; i++) {
            Class<?>[] parameterTypes = candidates[i].getParameterTypes();
            if (parameterTypes.length != cargs.getArgumentCount()) {
                continue;
            }
            argsToUse = new Object[parameterTypes.length];
            boolean result = this.valueMatchTypes(parameterTypes,
                    cargs.getArgumentValues(),
                    argsToUse,
                    valueResolver,
                    typeConverter);
            if (result) {
                constructorToUse = candidates[i];
                break;
            }
        }

        if (constructorToUse == null) {
            throw new BeanCreationException(bd.getID(), "can't find a apporiate constructor");
        }

        try {
            return constructorToUse.newInstance(argsToUse);
        } catch (Exception e) {
            throw new BeanCreationException(bd.getID(), "can't find a create instance using " + constructorToUse);
        }
    }

    private boolean valueMatchTypes(Class<?>[] parameterTypes,
                                    List<ConstructorArgument.ValueHolder> argumentValues,
                                    Object[] argsToUse,
                                    BeanDefinitionValueResolver valueResolver,
                                    SimpleTypeConverter typeConverter) {

        for (int i = 0; i < parameterTypes.length; i++) {
            ConstructorArgument.ValueHolder valueHolder = argumentValues.get(i);
            //获取参数的包装数据结构
            Object value = valueHolder.getValue();
            try {
                //获取对象与解析
                Object resolvedValue = valueResolver.resolveValueIfNecessary(value);
                Object convertedValue = typeConverter.convertIfNecessary(resolvedValue, parameterTypes[i]);
                //参数赋值
                argsToUse[i] = convertedValue;
            } catch (TypeMismatchException e) {
                logger.debug(e);
                return false;
            }
        }
        return true;
    }
}
