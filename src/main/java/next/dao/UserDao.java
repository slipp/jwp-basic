package next.dao;

import java.sql.SQLException;
import java.util.List;

import core.jdbc.JdbcTemplate;
import next.model.User;

public class UserDao {

    public void insert(User user) throws SQLException {

        final String sql = "INSERT INTO USERS VALUES (${userId}, ${password}, ${name}, ${email})";
        new JdbcTemplate().update(sql, user);
    }

    public void update(User user) throws SQLException {
        final String sql = "UPDATE USERS SET password = ${password}, name = ${name}, email = ${email} WHERE userId = ${userId}";
        new JdbcTemplate().update(sql, user);
    }

    public List<User> findAll() throws SQLException {
        String sql = "SELECT userid, password, name, email FROM USERS";
        return new JdbcTemplate().select(sql, User.class);
    }

    public User findByUserId(String userId) throws SQLException {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=${userId}";
        User user = new User(userId, null, null, null);

        return new JdbcTemplate().selectOne(sql, User.class, user);
    }
}
