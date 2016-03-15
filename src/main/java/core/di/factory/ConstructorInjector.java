package core.di.factory;

import java.util.Set;

public class ConstructorInjector extends AbstractorInjector {
	public ConstructorInjector(Set<Class<?>> preInstanticateBeans, BeanFactory beanFactory) {
		super(preInstanticateBeans, beanFactory);
	}
	
	@Override
	void injectInline(Class<?> clazz, Set<Class<?>> preInstanticateBeans, BeanFactory beanFactory) {
		instantiateClass(clazz);
	}
}
