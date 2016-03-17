package core.di.factory.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

import com.google.common.collect.Sets;

import core.di.factory.BeanFactoryUtils;
import core.di.factory.config.BeanDefinition;
import core.di.factory.config.InjectType;

public class DefaultBeanDefinition implements BeanDefinition {
	private Class<?> beanClazz;
	private Constructor<?> constructor;
	private Set<Class<?>> injectProperties;

	public DefaultBeanDefinition(Class<?> clazz) {
		this.beanClazz = clazz;
		constructor = getInjectConstructor(clazz);
		injectProperties = getInjectPropertiesType(clazz, constructor);
	}

	private static Constructor<?> getInjectConstructor(Class<?> clazz) {
		return BeanFactoryUtils.getInjectedConstructor(clazz);
	}

	private static Set<Class<?>> getInjectPropertiesType(Class<?> clazz, Constructor<?> constructor) {
		if (constructor != null) {
			return Sets.newHashSet();
		}

		Set<Class<?>> injectProperties = Sets.newHashSet();
		Set<Method> injectMethod = BeanFactoryUtils.getInjectedMethods(clazz);
		for (Method method : injectMethod) {
			Class<?>[] paramTypes = method.getParameterTypes();
			if (paramTypes.length != 1) {
				throw new IllegalStateException("DI할 메소드 인자는 하나여야 합니다.");
			}

			injectProperties.add(paramTypes[0]);
		}

		Set<Field> injectField = BeanFactoryUtils.getInjectedFields(clazz);
		for (Field field : injectField) {
			injectProperties.add(field.getType());
		}
		return injectProperties;
	}

	public Constructor<?> getConstructor() {
		return constructor;
	}

	public Set<Class<?>> getInjectProperties() {
		return injectProperties;
	}

	@Override
	public Class<?> getBeanClass() {
		return this.beanClazz;
	}

	@Override
	public InjectType getResolvedInjectMode() {
		if (constructor != null) {
			return InjectType.INJECT_CONSTRUCTOR;
		}

		if (!injectProperties.isEmpty()) {
			return InjectType.INJECT_TYPE;
		}

		return InjectType.INJECT_NO;
	}
}
