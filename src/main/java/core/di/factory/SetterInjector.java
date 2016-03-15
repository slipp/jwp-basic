package core.di.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetterInjector extends AbstractInjector {
	private static final Logger logger = LoggerFactory.getLogger(SetterInjector.class);
	
	public SetterInjector(Set<Class<?>> preInstanticateBeans, BeanFactory beanFactory) {
		super(preInstanticateBeans, beanFactory);
	}
	
	@Override
	void injectInline(Class<?> clazz, Set<Class<?>> preInstanticateBeans, BeanFactory beanFactory) {
		Set<Method> injectedMethods = BeanFactoryUtils.getInjectedMethods(clazz);
		for (Method method : injectedMethods) {
			logger.debug("invoke method : {}", method);
			Class<?>[] paramTypes = method.getParameterTypes();
			if (paramTypes.length != 1) {
				throw new IllegalStateException("DI할 메소드 인자는 하나여야 합니다.");
			}
			
			Class<?> concreteClazz = findBeanClass(paramTypes[0], preInstanticateBeans);
			Object bean = beanFactory.getBean(concreteClazz);
			if (bean == null) {
				bean = instantiateClass(concreteClazz);
			}
			try {
				method.invoke(beanFactory.getBean(method.getDeclaringClass()), bean);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				logger.error(e.getMessage());
			}
		}
	}
}
