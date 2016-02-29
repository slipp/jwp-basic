package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import next.model.Answer;
import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;

public class AnswerDao {
    private Long newAnswerId() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT max(answerId) as answerId FROM ANSWERS";
        RowMapper<Long> rm = new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet rs) throws SQLException {
                return rs.getLong("answerId");
            }
        };
        return jdbcTemplate.queryForObject(sql, rm) + 1;
    }

    public Answer insert(Answer answer) {
        Long newAnswerId = newAnswerId();
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO ANSWERS (answerId, writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, newAnswerId, answer.getWriter(), answer.getContents(),
                new Timestamp(answer.getTimeFromCreateDate()), answer.getQuestionId());
        return findById(newAnswerId);
    }

    public Answer findById(long answerId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId = ?";

        RowMapper<Answer> rm = new RowMapper<Answer>() {
            @Override
            public Answer mapRow(ResultSet rs) throws SQLException {
                return new Answer(rs.getLong("answerId"), rs.getString("writer"), rs.getString("contents"),
                        rs.getTimestamp("createdDate"), rs.getLong("questionId"));
            }
        };

        return jdbcTemplate.queryForObject(sql, rm, answerId);
    }

    public List<Answer> findAllByQuestionId(long questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT answerId, writer, contents, createdDate FROM ANSWERS WHERE questionId = ? "
                + "order by answerId desc";

        RowMapper<Answer> rm = new RowMapper<Answer>() {
            @Override
            public Answer mapRow(ResultSet rs) throws SQLException {
                return new Answer(rs.getLong("answerId"), rs.getString("writer"), rs.getString("contents"),
                        rs.getTimestamp("createdDate"), questionId);
            }
        };

        return jdbcTemplate.query(sql, rm, questionId);
    }

	public void delete(Long answerId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "DELETE FROM ANSWERS WHERE answerId = ?";
        jdbcTemplate.update(sql, answerId);
	}
}
