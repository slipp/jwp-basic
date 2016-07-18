package next.dao;

import static org.junit.Assert.assertEquals;
import next.config.MyConfiguration;
import next.model.User;

import org.junit.Before;
import org.junit.Test;

import core.di.factory.AnnotationConfigApplicationContext;

public class UserDaoTest {
    private UserDao userDao;

    @Before
    public void setup() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
        userDao = ac.getBean(UserDao.class);
    }

    @Test
    public void crud() throws Exception {
        User expected = new User("userId", "password", "name", "javajigi@email.com");
        userDao.insert(expected);

        User actual = userDao.findByUserId(expected.getUserId());
        assertEquals(expected, actual);
    }

}
