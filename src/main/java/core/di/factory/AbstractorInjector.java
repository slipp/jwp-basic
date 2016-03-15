package core.di.factory;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.google.common.collect.Lists;

public abstract class AbstractorInjector implements Injector {
	private static final Logger logger = LoggerFactory.getLogger(ConstructorInjector.class);
	
	private Set<Class<?>> preInstanticateBeans;

	private BeanFactory beanFactory;

	public AbstractorInjector(Set<Class<?>> preInstanticateBeans, BeanFactory beanFactory) {
		this.preInstanticateBeans = preInstanticateBeans;
		this.beanFactory = beanFactory;
	}
	
	@Override
	public void inject(Class<?> clazz) {
		injectInline(clazz, preInstanticateBeans, beanFactory);
	}
	
	abstract void injectInline(Class<?> clazz, Set<Class<?>> preInstanticateBeans, BeanFactory beanFactory);
	
    protected Object instantiateClass(Class<?> clazz) {
        Object bean = beanFactory.getBean(clazz);
        if (bean != null) {
            return bean;
        }
        
        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);
        if (injectedConstructor == null) {
            bean = BeanUtils.instantiate(clazz);
            beanFactory.registerBean(clazz, bean);
            return bean;
        }
        
        logger.debug("Constructor : {}", injectedConstructor);
        bean = instantiateConstructor(injectedConstructor);
        beanFactory.registerBean(clazz, bean);
        return bean;
    }

    private Object instantiateConstructor(Constructor<?> constructor) {
        Class<?>[] pTypes = constructor.getParameterTypes();
        List<Object> args = Lists.newArrayList();
        for (Class<?> clazz : pTypes) {
            Class<?> concreteClazz = findBeanClass(clazz, preInstanticateBeans);
            Object bean = beanFactory.getBean(concreteClazz);
            if (bean == null) {
                bean = instantiateClass(concreteClazz);
            }
            args.add(bean);
        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }
    
    protected Class<?> findBeanClass(Class<?> clazz, Set<Class<?>> preInstanticateBeans) {
    	Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
        if (!preInstanticateBeans.contains(concreteClazz)) {
            throw new IllegalStateException(clazz + "는 Bean이 아니다.");
        }
        return concreteClazz;
    }
}
