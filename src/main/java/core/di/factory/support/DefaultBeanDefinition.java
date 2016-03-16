package core.di.factory.support;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import core.di.factory.BeanFactoryUtils;
import core.di.factory.config.BeanDefinition;
import core.di.factory.config.InjectType;

public class DefaultBeanDefinition implements BeanDefinition {
    private Class<?> beanClazz;
    private List<Class<?>> constructorArguments;
    private Set<Class<?>> injectProperties;

	public DefaultBeanDefinition(Class<?> clazz) {
    	this.beanClazz = clazz;
    	constructorArguments = getInjectConstructorArguments(clazz);
   		injectProperties = getInjectPropertiesType(clazz, constructorArguments);
    }
	
	private static List<Class<?>> getInjectConstructorArguments(Class<?> clazz) {
		Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(clazz);
		if (constructor == null) {
			return Lists.newArrayList();
		}
		return Arrays.asList(constructor.getParameterTypes());
	}
	
	private static Set<Class<?>> getInjectPropertiesType(Class<?> clazz, List<Class<?>> constructorArguments) {
		if (!constructorArguments.isEmpty()) {
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
	
	public List<Class<?>> getConstructorArguments() {
		return constructorArguments;
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
		if (constructorArguments.isEmpty() && injectProperties.isEmpty()) {
			return InjectType.INJECT_NO;
		}
		
		if (!constructorArguments.isEmpty()) {
			return InjectType.INJECT_CONSTRUCTOR;
		}
		
		return InjectType.INJECT_TYPE;
	}
}
