package core.web.mvc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.di.factory.ApplicationContext;

public class AnnotationHandlerMapping implements HandlerMapping {
	private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);
	
	private Object[] basePackages;
	
	private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();
	
	public AnnotationHandlerMapping(Object... basePackages) {
		this.basePackages = basePackages;
	}
	
	public void initialize() {
		ApplicationContext ac = new ApplicationContext(basePackages);
		Map<Class<?>, Object> controllers = getControllers(ac);
		Set<Method> methods = getRequestMappingMethods(controllers.keySet());
		for (Method method : methods) {
			RequestMapping rm = method.getAnnotation(RequestMapping.class);
			logger.debug("register handlerExecution : url is {}, method is {}", rm.value(), method);
			handlerExecutions.put(createHandlerKey(rm), new HandlerExecution(controllers.get(method.getDeclaringClass()), method));
		}
		
		logger.info("Initialized AnnotationHandlerMapping!");
	}
	
	private Map<Class<?>, Object> getControllers(ApplicationContext ac) {
		Map<Class<?>, Object> controllers = Maps.newHashMap();
		for (Class<?> clazz : ac.getBeanClasses()) {
			Annotation annotation = clazz.getAnnotation(Controller.class);
			if (annotation != null) {
				controllers.put(clazz, ac.getBean(clazz));
			}
		}
		return controllers;
	}
	
	private HandlerKey createHandlerKey(RequestMapping rm) {
		return new HandlerKey(rm.value(), rm.method());
	}
	
	@SuppressWarnings("unchecked")
	private Set<Method> getRequestMappingMethods(Set<Class<?>> controlleers) {
		Set<Method> requestMappingMethods = Sets.newHashSet();
		for (Class<?> clazz : controlleers) {
			requestMappingMethods.addAll(ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class)));
		}
		return requestMappingMethods;
	}

	@Override
	public HandlerExecution getHandler(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
		logger.debug("requestUri : {}, requestMethod : {}", requestUri, rm);
		return handlerExecutions.get(new HandlerKey(requestUri, rm));
	}
}
