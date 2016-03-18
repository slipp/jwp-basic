package next.dao;

import static org.junit.Assert.assertEquals;
import next.config.MyConfiguration;
import next.model.User;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.di.factory.ApplicationContext;
import core.jdbc.ConnectionManager;

public class UserDaoTest {
	private UserDao userDao;
	
    @Before
    public void setup() {
    	ApplicationContext ac = new ApplicationContext(MyConfiguration.class);
    	userDao = ac.getBean(UserDao.class);
    	
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

	@Test
	public void crud() throws Exception {
		User expected = new User("userId", "password", "name", "javajigi@email.com");
		userDao.insert(expected);
		
		User actual = userDao.findByUserId(expected.getUserId());
		assertEquals(expected, actual);
	}

}
