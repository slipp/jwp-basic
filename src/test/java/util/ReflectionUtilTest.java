package util;

import next.model.User;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by johngrib on 2017. 4. 22..
 */
public class ReflectionUtilTest {

    @Test
    public void objMapper_테스트() {

        final User user = new User("id", "password", "name", null);
        user.setAge(11);

        final Map<String, Object> map = ReflectionUtil.objMapper(user, new UpperStringMap());

        assertThat(user.getUserId(), is(map.get("userId")));
        assertThat(user.getPassword(), is(map.get("password")));
        assertThat(user.getName(), is(map.get("name")));
        assertThat(user.getAge(), is(map.get("age")));
    }

    @Test
    public void createVo_테스트() {

        final User user = new User("id", "password", "name", null);
        user.setAge(11);

        final Map<String, Object> map = new UpperStringMap() {{
            put("userId", "id");
            put("password", "password");
            put("name", "name");
            put("age", 11);
        }};

        User user2 = ReflectionUtil.createVo(User.class, map);
        assertEquals(user, user2);
    }

    @Test
    public void getMemberList_테스트() {

        Map<String, DataMethod> getters = ReflectionUtil.getGetterMemberMap(User.class);

        assertTrue(getters.containsKey("userId"));
        assertTrue(getters.containsKey("name"));
        assertTrue(getters.containsKey("password"));
        assertTrue(getters.containsKey("email"));
    }
}