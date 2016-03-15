package core.di;

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

import core.annotation.Inject;

public class ConstructorInjector {
	private static final Logger logger = LoggerFactory.getLogger(ConstructorInjector.class);
	
	private Set<Class<?>> preInstanticateClazz;
	private Map<Class<?>, Object> beans = Maps.newHashMap();

	public ConstructorInjector(Set<Class<?>> preInstanticateClazz) {
		this.preInstanticateClazz = preInstanticateClazz;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> requiredType) {
		return (T)beans.get(requiredType);
	}

	public void inject() {
		for (Class<?> clazz : preInstanticateClazz) {
			if (beans.get(clazz) == null) {
				instantiate(clazz);
			}
		}
		
		for (Class<?> clazz : beans.keySet()) {
			logger.debug("Bean : {}", beans.get(clazz));
		}
	}

	private Object instantiate(Class<?> clazz) {
		if (beans.get(clazz) != null) {
			return beans.get(clazz);
		}
		
		Constructor<?> injectedConstructor = getInjectedConstructor(clazz);
		if (injectedConstructor == null) {
			Object bean = newInstance(clazz);
			beans.put(clazz, bean);
			return bean;
		}
		
		logger.debug("Constructor : {}", injectedConstructor);
		Object bean = instantiate(injectedConstructor);
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
			if (!preInstanticateClazz.contains(clazz)) {
				throw new IllegalStateException(clazz + "는 Bean이 아니다.");
			}
			
			Object bean = beans.get(clazz);
			if (bean == null) {
				bean = instantiate(clazz);
			}
			args.add(bean);
		}
		return BeanUtils.instantiateClass(constructor, args.toArray());
	}
	
	private Object newInstance(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e.getMessage());
			return null;
		}
	}
}
