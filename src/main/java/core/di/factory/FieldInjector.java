package core.di.factory;

import java.lang.reflect.Field;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldInjector extends AbstractorInjector {
	private static final Logger logger = LoggerFactory.getLogger(FieldInjector.class);

	public FieldInjector(Set<Class<?>> preInstanticateBeans, BeanFactory beanFactory) {
		super(preInstanticateBeans, beanFactory);
	}

	@Override
	void injectInline(Class<?> clazz, Set<Class<?>> preInstanticateBeans, BeanFactory beanFactory) {
		Set<Field> injectedFields = BeanFactoryUtils.getInjectedFields(clazz);
		for (Field field : injectedFields) {
			logger.debug("invoke field : {}", field);
			Class<?> concreteClazz = findBeanClass(field.getType(), preInstanticateBeans);
			Object bean = beanFactory.getBean(concreteClazz);
			if (bean == null) {
				bean = instantiateClass(concreteClazz);
			}
			try {
				field.setAccessible(true);
				field.set(beanFactory.getBean(field.getDeclaringClass()), bean);
			} catch (IllegalAccessException | IllegalArgumentException e) {
				logger.error(e.getMessage());
			}
		}
	}
}
