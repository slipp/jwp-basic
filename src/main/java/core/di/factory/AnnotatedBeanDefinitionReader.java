package core.di.factory;

import java.lang.reflect.Method;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AnnotatedBeanDefinitionReader {
	private static final Logger log = LoggerFactory.getLogger(AnnotatedBeanDefinitionReader.class);
	
	private BeanDefinitionRegistry beanDefinitionRegistry;

	public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
		this.beanDefinitionRegistry = beanDefinitionRegistry;
	}

	public void register(Class<?>... annotatedClasses) {
		for (Class<?> annotatedClass : annotatedClasses) {
			registerBean(annotatedClass);
		}
	}

	public void registerBean(Class<?> annotatedClass) {
		beanDefinitionRegistry.registerBeanDefinition(annotatedClass, new BeanDefinition(annotatedClass));
		Set<Method> beanMethods = BeanFactoryUtils.getBeanMethods(annotatedClass);
		for (Method beanMethod : beanMethods) {
			log.debug("@Bean method : {}", beanMethod);
			AnnotatedBeanDefinition abd = new AnnotatedBeanDefinition(beanMethod.getReturnType(), beanMethod);
			beanDefinitionRegistry.registerBeanDefinition(beanMethod.getReturnType(), abd);
		}
	}
}
