package core.di.factory.support;

import core.di.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {
    void registerBeanDefinition(Class<?> clazz, BeanDefinition beanDefinition);
}
