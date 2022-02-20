package core.ref;

import org.junit.Test;

import java.lang.reflect.Method;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        Method[] ary = clazz.getMethods();
        for (Method tmp : ary) {
            if (tmp.isAnnotationPresent(MyTest.class)) {
                tmp.invoke(clazz.newInstance());
            }
        }
    }
}
