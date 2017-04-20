package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.JdbcTemplate;
import next.model.User;

public class UserDao {
    private final JdbcTemplate template;

    public UserDao() {
        template = new JdbcTemplate();
    }
    public void insert(User user) {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        template.insert(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public void update(User user) {
        String sql = "UPDATE \"USERS\" SET password =?, name = ?, email = ? WHERE userId = ?";
        template.insert(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM USERS";
        return template.findAll(sql, UserDao::mapRow);
    }

    public User findByUserId(String userId)  {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return template.queryForObject(sql, UserDao::mapRow, userId).get();
    }

    private static User mapRow(ResultSet rs) throws SQLException {
        return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                rs.getString("email"));
    }
}
