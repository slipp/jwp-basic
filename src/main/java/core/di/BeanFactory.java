package core.di;

import java.util.Map;

public class BeanFactory {
	private Map<Class<?>, Object> beans;
	
	void registerBean(Class<?> key, Object bean) {
		beans.put(key, bean);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> requiredType) {
		return (T)beans.get(requiredType);
	}
}
