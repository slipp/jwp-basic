package core.ref;

import org.junit.Test;

import java.lang.reflect.Method;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        Method[] ary = clazz.getMethods();
        for (Method str : ary) {
            if (str.getName().startsWith("test")) {
                str.invoke(clazz.newInstance());
            }
        }
    }
}
