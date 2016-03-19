package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import next.model.Answer;

@Repository
public class JdbcAnswerDao implements AnswerDao {
	private JdbcTemplate jdbcTemplate;
	
	@Inject
    public JdbcAnswerDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
    @Override
	public Answer insert(Answer answer) {
        String sql = "INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";
        PreparedStatementCreator psc = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, answer.getWriter());
				pstmt.setString(2, answer.getContents());
				pstmt.setTimestamp(3, new Timestamp(answer.getTimeFromCreateDate()));
				pstmt.setLong(4, answer.getQuestionId());
				return pstmt;
			}
		};
        
		KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        return findById(keyHolder.getKey().longValue());
    }

    @Override
	public Answer findById(long answerId) {
        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId = ?";

        RowMapper<Answer> rm = new RowMapper<Answer>() {
            @Override
            public Answer mapRow(ResultSet rs, int index) throws SQLException {
                return new Answer(rs.getLong("answerId"), rs.getString("writer"), rs.getString("contents"),
                        rs.getTimestamp("createdDate"), rs.getLong("questionId"));
            }
        };

        return jdbcTemplate.queryForObject(sql, rm, answerId);
    }

    @Override
	public List<Answer> findAllByQuestionId(long questionId) {
        String sql = "SELECT answerId, writer, contents, createdDate FROM ANSWERS WHERE questionId = ? "
                + "order by answerId desc";

        RowMapper<Answer> rm = new RowMapper<Answer>() {
            @Override
            public Answer mapRow(ResultSet rs, int index) throws SQLException {
                return new Answer(rs.getLong("answerId"), rs.getString("writer"), rs.getString("contents"),
                        rs.getTimestamp("createdDate"), questionId);
            }
        };

        return jdbcTemplate.query(sql, rm, questionId);
    }

	@Override
	public void delete(Long answerId) {
        String sql = "DELETE FROM ANSWERS WHERE answerId = ?";
        jdbcTemplate.update(sql, answerId);
	}
}
