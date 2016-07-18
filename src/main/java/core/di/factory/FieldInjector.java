package core.di.factory;

import java.lang.reflect.Field;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldInjector extends AbstractInjector {
    private static final Logger logger = LoggerFactory.getLogger(FieldInjector.class);

    public FieldInjector(BeanFactory beanFactory) {
        super(beanFactory);
    }

    Set<?> getInjectedBeans(Class<?> clazz) {
        return BeanFactoryUtils.getInjectedFields(clazz);
    }

    void inject(Object injectedBean, Object bean, BeanFactory beanFactory) {
        Field field = (Field) injectedBean;
        try {
            field.setAccessible(true);
            field.set(beanFactory.getBean(field.getDeclaringClass()), bean);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            logger.error(e.getMessage());
        }
    }

    Class<?> getBeanClass(Object injectedBean) {
        Field field = (Field) injectedBean;
        return field.getType();
    }
}
