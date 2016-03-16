package core.di.factory.config;

import java.util.List;
import java.util.Set;

public interface BeanDefinition {
    Class<?> getBeanClass();
    
    InjectType getResolvedInjectMode();
    
    List<Class<?>> getConstructorArguments();
    
    Set<Class<?>> getInjectProperties();
}
