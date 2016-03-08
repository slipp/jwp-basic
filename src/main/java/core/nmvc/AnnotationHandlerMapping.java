package core.nmvc;

import static org.reflections.ReflectionUtils.getAllMethods;
import static org.reflections.ReflectionUtils.withAnnotation;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import core.annotation.RequestMapping;
import core.annotation.RequestMethod;

public class AnnotationHandlerMapping {
	private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);
	
	private Object[] basePackage;
	
	private Map<HandlerKey, HandlerExecution> handlerExecutions;
	
	public AnnotationHandlerMapping(Object... basePackage) {
		this.basePackage = basePackage;
		this.handlerExecutions= Maps.newHashMap(); 
	}
	
	public void initialize() {
		ControllerFactory controllerFactory = new ControllerFactory(basePackage);
		Map<Class<?>, Object> controllers = controllerFactory.getControllers();
		Set<Method> methods = getRequestMappingMethods(controllers.keySet());
		for (Method method : methods) {
			RequestMapping rm = method.getAnnotation(RequestMapping.class);
			logger.debug("register handlerExecution : url is {}, method is {}", rm.value(), method);
			handlerExecutions.put(createHandlerKey(rm), new HandlerExecution(controllers.get(method.getDeclaringClass()), method));
		}
	}
	
	private HandlerKey createHandlerKey(RequestMapping rm) {
		return new HandlerKey(rm.value(), rm.method());
	}
	
	@SuppressWarnings("unchecked")
	Set<Method> getRequestMappingMethods(Set<Class<?>> beans) {
		Set<Method> requestMappingMethods = Sets.newHashSet();
		for (Class<?> clazz : beans) {
			requestMappingMethods.addAll(getAllMethods(clazz, withAnnotation(RequestMapping.class)));
		}
		return requestMappingMethods;
	}

	public HandlerExecution getHandler(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
		logger.debug("requestUri : {}, requestMethod : {}", requestUri, rm);
		return handlerExecutions.get(new HandlerKey(requestUri, rm));
	}
}
