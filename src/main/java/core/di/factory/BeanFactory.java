package core.di.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import core.annotation.Controller;
import core.di.factory.config.BeanDefinition;
import core.di.factory.config.InjectType;
import core.di.factory.support.BeanDefinitionRegistry;

public class BeanFactory implements BeanDefinitionRegistry {
	private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);
	
	private Map<Class<?>, Object> beans = Maps.newHashMap();
	
	private Map<Class<?>, BeanDefinition> beanDefinitions = Maps.newHashMap();
	
	public BeanFactory() {
		
	}

	public Set<Class<?>> getPreInstanticateBeans() {
		return beanDefinitions.keySet();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> requiredType) {
		return (T)beans.get(requiredType);
	}
	
    public void initialize() {
    	Set<Class<?>> beanClasses = beanDefinitions.keySet();
    	for (Class<?> clazz : beanClasses) {
			BeanDefinition beanDefinition = beanDefinitions.get(clazz);
			inject(beanDefinition);
		}
    }

	private void inject(BeanDefinition beanDefinition) {
		if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_NO) {
			instantiateClass(beanDefinition.getBeanClass());
		} else if (beanDefinition.getResolvedInjectMode() == InjectType.INJECT_TYPE){
			injectProperties(beanDefinition);
		} else {
			instantiateClass(beanDefinition.getBeanClass());
		}
	}

	private Object injectProperties(BeanDefinition beanDefinition) {
		Class<?> beanClass = beanDefinition.getBeanClass();
		Object bean = BeanUtils.instantiate(beanClass);
		Field[] fields = beanClass.getDeclaredFields();
		for (Field field : fields) {
			logger.debug("Inject Bean : {}, Field : {}", beanClass, field);
			Class<?> concreteClass = findBeanClass(field.getType(), getPreInstanticateBeans());
			try {
				field.setAccessible(true);
				field.set(bean, instantiateClass(concreteClass));
			} catch (IllegalAccessException | IllegalArgumentException e) {
				logger.error(e.getMessage());
			}
		}
		registerBean(beanClass, bean);
		return bean;
	}
	
	private Object instantiateClass(Class<?> clazz) {
		Class<?> concreteClass = findBeanClass(clazz, getPreInstanticateBeans());
        Object bean = getBean(concreteClass);
        if (bean != null) {
            return bean;
        }
        
        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(concreteClass);
        if (injectedConstructor == null) {
            bean = injectProperties(beanDefinitions.get(concreteClass));
            registerBean(concreteClass, bean);
            return bean;
        }
        
        logger.debug("Constructor : {}", injectedConstructor);
        bean = instantiateConstructor(injectedConstructor);
        registerBean(concreteClass, bean);
        return bean;
    }

    private Object instantiateConstructor(Constructor<?> constructor) {
        Class<?>[] pTypes = constructor.getParameterTypes();
        List<Object> args = Lists.newArrayList();
        for (Class<?> clazz : pTypes) {
            Class<?> concreteClazz = findBeanClass(clazz, getPreInstanticateBeans());
            Object bean = getBean(concreteClazz);
            if (bean == null) {
                bean = instantiateClass(concreteClazz);
            }
            args.add(bean);
        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }
    
    private Class<?> findBeanClass(Class<?> clazz, Set<Class<?>> preInstanticateBeans) {
    	Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
        if (!preInstanticateBeans.contains(concreteClazz)) {
            throw new IllegalStateException(clazz + "는 Bean이 아니다.");
        }
        return concreteClazz;
    }
    
    void registerBean(Class<?> clazz, Object bean) {
    	beans.put(clazz, bean);
    }

	public Map<Class<?>, Object> getControllers() {
		Map<Class<?>, Object> controllers = Maps.newHashMap();
		for (Class<?> clazz : getPreInstanticateBeans()) {
			Annotation annotation = clazz.getAnnotation(Controller.class);
			if (annotation != null) {
				controllers.put(clazz, beans.get(clazz));
			}
		}
		return controllers;
	}
	
	void clear() {
		beanDefinitions.clear();
		beans.clear();
	}

	@Override
	public void registerBeanDefinition(Class<?> clazz, BeanDefinition beanDefinition) {
		logger.debug("register bean : {}", clazz);
		beanDefinitions.put(clazz, beanDefinition);
	}
}
