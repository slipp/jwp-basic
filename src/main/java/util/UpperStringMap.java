package util;

import java.util.HashMap;

/**
 * key 를 대문자로만 취급하는 HashMap. ResultSet 의 ColumnName 이 대문자만 취급해서 만들었다.
 * Created by johngrib on 2017. 4. 22..
 */
public class UpperStringMap extends HashMap {

    @Override
    public Object get(Object key) {
        return super.get(key.toString().toUpperCase());
    }

    @Override
    public Object put(Object key, Object value) {
        return super.put(key.toString().toUpperCase(), value);
    }

    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(key.toString().toUpperCase());
    }
}
