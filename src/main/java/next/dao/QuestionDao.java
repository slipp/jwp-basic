package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import next.model.Question;
import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import core.jdbc.PreparedStatementCreator;
import core.jdbc.RowMapper;



public class QuestionDao {
	private static QuestionDao sgtQuestionDao = new QuestionDao();
	public static QuestionDao getInstance(){
		return sgtQuestionDao;
	}
	private QuestionDao(){}
	public List<Question> findAll() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT questionId, writer, title, createdDate, countOfAnswer FROM QUESTIONS "
				+ "order by questionId desc";
		
		RowMapper<Question> rm = new RowMapper<Question>() {
			@Override
			public Question mapRow(ResultSet rs) throws SQLException {
				return new Question(rs.getLong("questionId"),
						rs.getString("writer"), rs.getString("title"), null,
						rs.getTimestamp("createdDate"),
						rs.getInt("countOfAnswer"));
			}
			
		};
		
		return jdbcTemplate.query(sql, rm);
	}

	public Question findById(long questionId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS "
				+ "WHERE questionId = ?";
		
		RowMapper<Question> rm = new RowMapper<Question>() {
			@Override
			public Question mapRow(ResultSet rs) throws SQLException {
				return new Question(rs.getLong("questionId"),
						rs.getString("writer"), rs.getString("title"),
						rs.getString("contents"),
						rs.getTimestamp("createdDate"),
						rs.getInt("countOfAnswer"));
			}
		};
		
		return jdbcTemplate.queryForObject(sql, rm, questionId);
	}
	
	public Question insert(Question question){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, ?, ?)";
        PreparedStatementCreator psc = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, question.getWriter());
				pstmt.setString(2, question.getTitle());
				pstmt.setString(3, question.getContents());
				pstmt.setTimestamp(4, new Timestamp(question.getTimeFromCreateDate()));
				pstmt.setLong(5, 0);
				return pstmt;
			}
		};
        
		KeyHolder keyHolder = new KeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        return findById(keyHolder.getId());
	}
	
	public void delete(Long questionId){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "DELETE FROM QUESTIONS WHERE questionId = ?";
        PreparedStatementCreator psc = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, questionId);
				return pstmt;
			}
		};
        
		KeyHolder keyHolder = new KeyHolder();
        jdbcTemplate.update(psc, keyHolder);
	}
	public void update(Question question){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE QUESTIONS SET writer = ?, title = ?, contents = ?, createdDate = ?, countOfAnswer = ? WHERE questionId = ?";
        PreparedStatementCreator psc = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, question.getWriter());
				pstmt.setString(2, question.getTitle());
				pstmt.setString(3, question.getContents());
				pstmt.setTimestamp(4, new Timestamp(question.getTimeFromCreateDate()));
				pstmt.setLong(5, question.getCountOfComment());
				pstmt.setLong(6, question.getQuestionId());
				return pstmt;
			}
		};
        
		KeyHolder keyHolder = new KeyHolder();
        jdbcTemplate.update(psc, keyHolder);
	}
	public void addCountOfAnswer(long questionId, int countOfComment){
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE QUESTIONS SET countOfAnswer = (?) WHERE questionId = (?)";
        PreparedStatementCreator psc = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, countOfComment);
				pstmt.setLong(2, questionId);
				return pstmt;
			}
		};		
		KeyHolder keyHolder = new KeyHolder();
        jdbcTemplate.update(psc, keyHolder);
	}
	public void delCountOfAnswer(long questionId, int countOfComment){
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE QUESTIONS SET countOfAnswer = (?) WHERE questionId = (?)";
        PreparedStatementCreator psc = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, countOfComment);
				pstmt.setLong(2, questionId);
				return pstmt;
			}
		};		
		KeyHolder keyHolder = new KeyHolder();
        jdbcTemplate.update(psc, keyHolder);
	}
}
