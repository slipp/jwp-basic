package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Getter, Setter Method Wrapper 로 사용하기 위해 만든 클래스.
 * Created by johngrib on 2017. 4. 22..
 */
public class DataMethod {

    final public Class type;
    final public String fieldName;
    final public Method method;

    public DataMethod(Class type, String field, Method method) {
        this.type = type;
        this.fieldName = field.toUpperCase();
        this.method = method;
    }

    public boolean isGetter() {
        return method.getReturnType() != null;
    }

    public boolean isSetter() {
        return method.getName().startsWith("set")
                && method.getParameterCount() == 1
                && !isGetter()
                ;
    }

    public Object getter(final Object invoker) {
        try {
            return method.invoke(invoker, new Object[]{});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setter(final Object invoker, final Object value) {
        try {
            method.invoke(invoker, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
