package core.di.factory.config;

import java.lang.reflect.Constructor;
import java.util.Set;

public interface BeanDefinition {
    Class<?> getBeanClass();
    
    InjectType getResolvedInjectMode();
    
    Constructor<?> getConstructor();
    
    Set<Class<?>> getInjectProperties();
}
