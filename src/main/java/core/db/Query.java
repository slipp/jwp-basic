package core.db;

import util.ReflectionUtil;
import util.UpperStringMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Query String 을 생성/관리한다.
 * Created by johngrib on 2017. 4. 22..
 */
public class Query {

    private static List<ValueProc> processors = Arrays.asList(new ValueProc[]{
            new NullProc(),
            new StringProc(),
            new IntegerProc()
    });

    private static String buildAlias(final String key) {
        return "(?i)" + Pattern.quote("${" + key + "}");
    }

    /**
     * query 에 vo 의 값을 set 한다.
     * query 에서 replace 될 키는 ${key} 의 형태로 지정한다.
     *
     * @param sql
     * @param vo
     * @return
     */
    public static String build(final String sql, final Object vo) {
        if (vo == null) {
            return sql;
        }

        final Map<String, Object> map = ReflectionUtil.objMapper(vo, new UpperStringMap());

        String sourceSql = sql;

        for (final String key : map.keySet()) {
            final Object val = map.get(key);
            final String value = processors.stream()
                    .filter(p -> p.typeCheck(val))
                    .findFirst().get().proc(val);
            sourceSql = sourceSql.replaceAll(buildAlias(key), value);
        }
        return sourceSql;
    }

    abstract static class ValueProc {
        abstract String proc(Object val);

        abstract boolean typeCheck(Object val);
    }

    static class StringProc extends ValueProc {
        @Override
        public String proc(final Object val) {
            return "'" + String.valueOf(val) + "'";
        }

        @Override
        boolean typeCheck(final Object val) {
            return val instanceof String;
        }
    }

    static class NullProc extends ValueProc {
        @Override
        public String proc(final Object val) {
            return "'" + String.valueOf(val) + "'";
        }

        @Override
        boolean typeCheck(final Object val) {
            return val == null;
        }
    }

    static class IntegerProc extends ValueProc {

        @Override
        String proc(final Object val) {
            return String.valueOf(val);
        }

        @Override
        boolean typeCheck(final Object val) {
            return val instanceof Integer;
        }
    }
}
