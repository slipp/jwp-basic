package core.di.context.annotation;

import java.lang.reflect.Method;

import core.di.beans.factory.support.DefaultBeanDefinition;

public class AnnotatedBeanDefinition extends DefaultBeanDefinition {
    private Method method;

    public AnnotatedBeanDefinition(Class<?> clazz, Method method) {
        super(clazz);
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }
}
