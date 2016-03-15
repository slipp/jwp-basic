package core.di.factory;

import static org.reflections.ReflectionUtils.getAllConstructors;
import static org.reflections.ReflectionUtils.withAnnotation;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import core.annotation.Inject;

public class BeanFactory {
	private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);
	
	private Set<Class<?>> preInstanticateBeans;

	private Map<Class<?>, Object> beans = Maps.newHashMap();

	public BeanFactory(Set<Class<?>> preInstanticateBeans) {
		this.preInstanticateBeans = preInstanticateBeans;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> requiredType) {
		return (T)beans.get(requiredType);
	}
	
	public void initialize() {
		for (Class<?> clazz : preInstanticateBeans) {
			if (beans.get(clazz) == null) {
				instantiate(clazz);
			}
		}
	}

	private Object instantiate(Class<?> clazz) {
		Object bean = beans.get(clazz);
		if (bean != null) {
			return bean;
		}
		
		Constructor<?> injectedConstructor = getInjectedConstructor(clazz);
		if (injectedConstructor == null) {
			bean = BeanUtils.instantiate(clazz);
			beans.put(clazz, bean);
			return bean;
		}
		
		logger.debug("Constructor : {}", injectedConstructor);
		bean = instantiate(injectedConstructor);
		beans.put(clazz, bean);
		return bean;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Constructor<?> getInjectedConstructor(Class<?> clazz) {
		Set<Constructor> injectedConstructors = getAllConstructors(clazz, withAnnotation(Inject.class));
		if (injectedConstructors.isEmpty()) {
			return null;
		}
		return injectedConstructors.iterator().next();
	}

	private Object instantiate(Constructor<?> constructor) {
		Class<?>[] pTypes = constructor.getParameterTypes();
		List<Object> args = Lists.newArrayList();
		for (Class<?> clazz : pTypes) {
			Class<?> concreteClazz = findConcreteClass(clazz);
			if (!preInstanticateBeans.contains(concreteClazz)) {
				throw new IllegalStateException(clazz + "는 Bean이 아니다.");
			}
			
			Object bean = beans.get(concreteClazz);
			if (bean == null) {
				bean = instantiate(concreteClazz);
			}
			args.add(bean);
		}
		return BeanUtils.instantiateClass(constructor, args.toArray());
	}
	
	private Class<?> findConcreteClass(Class<?> injectedClazz) {
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
