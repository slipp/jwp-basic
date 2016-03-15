package core.di.factory;

import static org.reflections.ReflectionUtils.getAllConstructors;
import static org.reflections.ReflectionUtils.withAnnotation;

import java.lang.reflect.Constructor;
import java.util.Set;

import com.google.common.collect.Sets;

import core.annotation.Inject;

public class BeanFactoryUtils {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Constructor<?> getInjectedConstructor(Class<?> clazz) {
		Set<Constructor> injectedConstructors = getAllConstructors(clazz, withAnnotation(Inject.class));
		if (injectedConstructors.isEmpty()) {
			return null;
		}
		return injectedConstructors.iterator().next();
	}
	
	public static Class<?> findConcreteClass(Class<?> injectedClazz, Set<Class<?>> preInstanticateBeans) {
		if (!injectedClazz.isInterface()) {
			return injectedClazz;
		}
		
		for (Class<?> clazz : preInstanticateBeans) {
			Set<Class<?>> interfaces = Sets.newHashSet(clazz.getInterfaces());
			if (interfaces.contains(injectedClazz)) {
				return clazz;
			}
		}
		
		throw new IllegalStateException(injectedClazz + "인터페이스를 구현하는 Bean이 존재하지 않는다.");
	}
}
