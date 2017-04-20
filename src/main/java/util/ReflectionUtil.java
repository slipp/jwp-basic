package util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReflectionUtil {

    static final String CLASS_REGEX = "^(.+)\\.class$";
    static final String DOT = ".";
    static final String SLASH = "/";

    /**
     * package 안에 정의된 모든 class 를 수집한다.
     */
    public static List<Class<?>> getClassesInPackage(final String packageName) {
        final String address = packageName.replace(DOT, SLASH);
        final URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(address);

        if (scannedUrl == null) {
            throw new IllegalArgumentException(packageName);
        }

        final File[] files = new File(scannedUrl.getFile()).listFiles();
        final List<Class<?>> classes = new ArrayList<>();

        for (final File file : files) {
            classes.addAll(find(file, packageName));
        }

        return classes;
    }

    /**
     * package 안에 정의된 모든 class 를 수집하여 name, class 의 Map 으로 만든다.
     *
     * @param packageName
     * @return
     */
    public static Map<String, Class<?>> collectClassesInPackage(final String packageName) {
        return getClassesInPackage(packageName)
                .stream()
                .collect(
                        Collectors.toMap(Class::getSimpleName, Function.identity())
                );
    }

    /**
     * 어노테이션이 붙은 클래스를 수집한다.
     *
     * @param classes
     * @param anno
     * @return
     */
    public static List<Class<?>> getAnnotatedClasses(List<Class<?>> classes, Class anno) {
        List<Class<?>> rs = new ArrayList<>();

        for (Class c : classes) {
            if (c.isAnnotationPresent(anno)) {
                rs.add(c);
            }
        }

        return rs;
    }

    /**
     * class 파일을 검색한다. [재귀] 사용.
     *
     * @param file
     * @param packageName
     * @return
     */
    private static List<Class<?>> find(final File file, final String packageName) {

        final List<Class<?>> classes = new ArrayList<Class<?>>();

        final String resource = packageName + DOT + file.getName();

        if (file.isDirectory()) {
            Arrays.stream(file.listFiles()).forEach(
                    subFile -> classes.addAll(find(subFile, resource))  // 재귀
            );
            return classes;
        }

        if (!resource.matches(CLASS_REGEX)) {
            return classes;
        }

        final String className = RegexUtil.exec(CLASS_REGEX, resource).get(1);

        try {
            classes.add(Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classes;
    }

    /**
     * getter 메소드를 사용하여 map 을 리턴한다.
     *
     * @param obj
     * @return
     */
    public static Map<String, Object> objMapper(final Object obj, final Map<String, Object> map) {

        final Map<String, DataMethod> getters = getGetterMemberMap(obj.getClass());

        for (final String key : getters.keySet()) {
            final Object val = getters.get(key).getter(obj);
            map.put(key, val);
        }
        return map;
    }

    /**
     * getter 메소드의 콜렉션을 리턴한다.
     *
     * @param c
     * @param <T>
     * @return
     */
    public static <T> Map<String, DataMethod> getGetterMemberMap(final Class<T> c) {

        final Map<String, DataMethod> map = new UpperStringMap();

        Arrays.stream(c.getDeclaredMethods())
                .filter(m -> m.getParameterCount() == 0)
                .filter(m -> m.getName().startsWith("get"))
                .forEach(m -> {
                    final Class type = m.getReturnType();
                    final String keyName = m.getName().replaceFirst("^get", "");
                    final String key = keyName.substring(0, 1).toLowerCase() + keyName.substring(1);
                    map.put(key, new DataMethod(type, key, m));
                });
        return map;
    }

    /**
     * setter 메소드의 콜렉션을 리턴한다.
     *
     * @param c
     * @param <T>
     * @return
     */
    public static <T> Map<String, DataMethod> getSetterMemberMap(final Class<T> c, final Map<String, DataMethod> map) {

        Arrays.stream(c.getDeclaredMethods())
                .filter(m -> m.getParameterCount() == 1)
                .filter(m -> m.getName().startsWith("set"))
                .forEach(m -> {
                    final String keyName = m.getName().replaceFirst("^set", "");
                    final String key = keyName.substring(0, 1).toLowerCase() + keyName.substring(1);
                    final Class type = m.getParameterTypes()[0];
                    map.put(key, new DataMethod(type, key, m));
                });
        return map;
    }

    /**
     * setter 메소드의 콜렉션을 리턴한다.
     * @param c
     * @param <T>
     * @return
     */
    public static <T> List<DataMethod> getSetterList(final Class<T> c) {
        Map<String, DataMethod> map = getSetterMemberMap(c, new UpperStringMap());
        return map.keySet()
                .stream()
                .map(key -> map.get(key)).collect(Collectors.toList());
    }

    /**
     * T 클래스의 기본 생성자를 호출하여 인스턴스를 생성한다.
     *
     * @param c
     * @param <T>
     * @return
     */
    public static <T> T newSimpleInstance(final Class<T> c) {
        try {
            final Constructor con = c.getConstructor(new Class[]{});
            return (T) con.newInstance(new Object[]{});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * VO 객체의 새로운 인스턴스를 생성하고 ^set 메서드를 통해 map 의 값을 set 한다.
     *
     * @param voClass
     * @param map
     * @param <VO>
     * @return
     */
    public static <VO> VO createVo(final Class<VO> voClass, final Map<String, Object> map) {

        final VO rs = newSimpleInstance(voClass);
        final Map<String, DataMethod> setters = getSetterMemberMap(voClass, new UpperStringMap());

        for (final String key : setters.keySet()) {
            final DataMethod m = setters.get(key);
            m.setter(rs, map.get(key));
        }
        return rs;
    }

}
