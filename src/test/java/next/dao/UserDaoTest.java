package next.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import next.model.User;

public class UserDaoTest {

	@Test
	public void crud() throws Exception {
		User expected = new User("userId", "password", "name", "javajigi@email.com");
		UserDao userDao = new UserDao();
		userDao.insert(expected);
		
		User actual = userDao.findByUserId(expected.getUserId());
		assertEquals(expected, actual);
	}

}
