package core.di.factory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import core.annotation.ComponentScan;

public class ApplicationContext {
	private static final Logger log = LoggerFactory.getLogger(ApplicationContext.class);
	
	private BeanFactory beanFactory;

	public ApplicationContext(Class<?>... annotatedClasses) {
		Object[] basePackages = findBasePackages(annotatedClasses);
		beanFactory = new BeanFactory();
		AnnotatedBeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
		abdr.register(annotatedClasses);
		
		if (basePackages.length > 0) {
			ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
			scanner.doScan(basePackages);
		}
		beanFactory.initialize();
	}

	private Object[] findBasePackages(Class<?>[] annotatedClasses) {
		List<Object> basePackages = Lists.newArrayList();
		for (Class<?> annotatedClass : annotatedClasses) {
			ComponentScan componentScan = annotatedClass.getAnnotation(ComponentScan.class);
			if (componentScan == null) {
				continue;
			}
			for(String basePackage : componentScan.value()) {
				log.info("Component Scan basePackage : {}", basePackage);
			}
			basePackages.addAll(Arrays.asList(componentScan.value()));
		}
		return basePackages.toArray();
	}

	public <T> T getBean(Class<T> clazz) {
		return beanFactory.getBean(clazz);
	}

	public Set<Class<?>> getBeanClasses() {
		return beanFactory.getBeanClasses();
	}
}
