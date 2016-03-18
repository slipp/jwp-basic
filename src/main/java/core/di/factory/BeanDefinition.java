package core.di.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

import com.google.common.collect.Sets;

public class BeanDefinition {
	private Class<?> beanClazz;
	private Constructor<?> injectConstructor;
	private Set<Field> injectFields;

	public BeanDefinition(Class<?> clazz) {
		this.beanClazz = clazz;
		injectConstructor = getInjectConstructor(clazz);
		injectFields = getInjectFields(clazz, injectConstructor);
	}

	private static Constructor<?> getInjectConstructor(Class<?> clazz) {
		return BeanFactoryUtils.getInjectedConstructor(clazz);
	}

	private Set<Field> getInjectFields(Class<?> clazz, Constructor<?> constructor) {
		if (constructor != null) {
			return Sets.newHashSet();
		}
		
		Set<Field> injectFields = Sets.newHashSet();
		Set<Class<?>> injectProperties = getInjectPropertiesType(clazz);
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (injectProperties.contains(field.getType())) {
				injectFields.add(field);
			}
		}
		return injectFields;
	}

	private static Set<Class<?>> getInjectPropertiesType(Class<?> clazz) {
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

	public Constructor<?> getInjectConstructor() {
		return injectConstructor;
	}

	public Set<Field> getInjectFields() {
		return this.injectFields;
	}

	public Class<?> getBeanClass() {
		return this.beanClazz;
	}

	public InjectType getResolvedInjectMode() {
		if (injectConstructor != null) {
			return InjectType.INJECT_CONSTRUCTOR;
		}

		if (!injectFields.isEmpty()) {
			return InjectType.INJECT_FIELD;
		}

		return InjectType.INJECT_NO;
	}
}
