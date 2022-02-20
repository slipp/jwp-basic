package core.nmvc;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public class ControllerScanner {
    private static final Logger log = LoggerFactory.getLogger(ControllerScanner.class);

    private Reflections reflections;

    public ControllerScanner(Object... backPackage) {
        reflections = new Reflections(backPackage);
    }

    public Map<Class<?>, Object> getControllers() {
        Set<Class<?>> preInitiatedControllers = reflections.getTypesAnnotatedWith(Controller.class);
        return instantiateControllers(preInitiatedControllers);
    }

    Map<Class<?>, Object> instantiateControllers(Set<Class<?>> preInitiatedControllers) {
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        try {
            for (Class<?> clazz : preInitiatedControllers) {
                controllers.put(clazz, clazz.newInstance());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return controllers;
    }
}
