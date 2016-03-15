package core.di.factory;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.reflections.Reflections;

import com.google.common.collect.Sets;

import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;

public class BeanScanner {
	private Reflections reflections;

	public BeanScanner(Object... basePackage) {
		reflections = new Reflections(basePackage);
	}

	@SuppressWarnings("unchecked")
	public Set<Class<?>> scan() {
		return getTypesAnnotatedWith(Controller.class, Service.class, Repository.class);
	}
	
	@SuppressWarnings("unchecked")
	private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
		Set<Class<?>> preInstantiatedBeans = Sets.newHashSet();
		for (Class<? extends Annotation> annotation : annotations) {
			preInstantiatedBeans.addAll(reflections.getTypesAnnotatedWith(annotation));
		}
		return preInstantiatedBeans;
	}
}
