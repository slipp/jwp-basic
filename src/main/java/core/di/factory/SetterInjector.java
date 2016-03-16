package core.di.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetterInjector extends AbstractInjector {
	private static final Logger logger = LoggerFactory.getLogger(SetterInjector.class);
	
	public SetterInjector(BeanFactory beanFactory) {
		super(beanFactory);
	}
	
	Set<?> getInjectedBeans(Class<?> clazz) {
		return BeanFactoryUtils.getInjectedMethods(clazz);
	}

	Class<?> getBeanClass(Object injectedBean) {
		Method method = (Method)injectedBean;
		logger.debug("invoke method : {}", method);
		Class<?>[] paramTypes = method.getParameterTypes();
		if (paramTypes.length != 1) {
			throw new IllegalStateException("DI할 메소드 인자는 하나여야 합니다.");
		}
		
		return paramTypes[0];
	}

	void inject(Object injectedBean, Object bean, BeanFactory beanFactory) {
		Method method = (Method)injectedBean;
		try {
			method.invoke(beanFactory.getBean(method.getDeclaringClass()), bean);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			logger.error(e.getMessage());
		}
	}
}
