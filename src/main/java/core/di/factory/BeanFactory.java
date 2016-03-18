package core.di.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class BeanFactory implements BeanDefinitionRegistry {
	private static final Logger log = LoggerFactory.getLogger(BeanFactory.class);
	
	private Map<Class<?>, Object> beans = Maps.newHashMap();
	
	private Map<Class<?>, BeanDefinition> beanDefinitions = Maps.newHashMap();
	
    public void initialize() {
    	for (Class<?> clazz : getBeanClasses()) {
			getBean(clazz);
		}
    }
    
    public Set<Class<?>> getBeanClasses() {
    	return beanDefinitions.keySet();
    }
    
	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> clazz) {
		Object bean = beans.get(clazz);
		if (bean != null) {
			return (T)bean;
		}
		
		BeanDefinition beanDefinition = beanDefinitions.get(clazz);
		if (beanDefinition != null && beanDefinition instanceof AnnotatedBeanDefinition) {
			Optional<Object> optionalBean = createAnnotatedBean(beanDefinition);
			optionalBean.ifPresent(b -> beans.put(clazz, b));
			return (T)optionalBean.orElse(null);
		}
		
		Optional<Class<?>> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, getBeanClasses());
		if (!concreteClazz.isPresent()) {
			return null;
		}
		
		beanDefinition = beanDefinitions.get(concreteClazz.get());
		bean = inject(beanDefinition);
		beans.put(concreteClazz.get(), bean);
		return (T)bean;
	}

	private Optional<Object> createAnnotatedBean(BeanDefinition beanDefinition) {
		AnnotatedBeanDefinition abd = (AnnotatedBeanDefinition) beanDefinition;
		Method method = abd.getMethod();
		Object[] args = populateArguments(method.getParameterTypes());
		try {
			return Optional.of(method.invoke(getBean(method.getDeclaringClass()), args));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			log.error(e.getMessage());
			return Optional.empty();
		}
	}

	private Object[] populateArguments(Class<?>[] paramTypes) {
		List<Object> args = Lists.newArrayList();
		for (Class<?> param : paramTypes) {
			Object bean = getBean(param);
			if (bean == null) {
				throw new NullPointerException(param + "에 해당하는 Bean이 존재하지 않습니다.");
			}
			args.add(getBean(param));
		}
		return args.toArray();
	}
	
	private Object inject(BeanDefinition beanDefinition) {
		if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_NO) {
			return BeanUtils.instantiate(beanDefinition.getBeanClass());
		} else if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_FIELD){
			return injectFields(beanDefinition);
		} else {
			return injectConstructor(beanDefinition);
		}
	}

	private Object injectConstructor(BeanDefinition beanDefinition) {
		Constructor<?> constructor = beanDefinition.getInjectConstructor();
		Object[] args = populateArguments(constructor.getParameterTypes());
		return BeanUtils.instantiateClass(constructor, args);
	}

	private Object injectFields(BeanDefinition beanDefinition) {
		Object bean = BeanUtils.instantiate(beanDefinition.getBeanClass());
		Set<Field> injectFields = beanDefinition.getInjectFields();
		for (Field field : injectFields) {
			injectField(bean, field);
		}
		return bean;
	}

	private void injectField(Object bean, Field field) {
		log.debug("Inject Bean : {}, Field : {}", bean, field);
		try {
			field.setAccessible(true);
			field.set(bean, getBean(field.getType()));
		} catch (IllegalAccessException | IllegalArgumentException e) {
			log.error(e.getMessage());
		}
	}

	public void clear() {
		beanDefinitions.clear();
		beans.clear();
	}

	@Override
	public void registerBeanDefinition(Class<?> clazz, BeanDefinition beanDefinition) {
		log.debug("register bean : {}", clazz);
		beanDefinitions.put(clazz, beanDefinition);
	}
}
