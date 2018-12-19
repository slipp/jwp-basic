package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.JdbcTemplate;
import next.model.Question;

public class QuestionDao {
	    @SuppressWarnings("unchecked")
		public List<Question> findAll() throws SQLException {
	      
	    	JdbcTemplate jdbcTemplate = new JdbcTemplate();
	    	
	        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS";
	        return (List<Question>) jdbcTemplate.query(sql,(ResultSet rs)->{
	      		return new Question(rs.getLong("questionId"),rs.getString("writer"),rs.getString("title"),rs.getString("contents"),rs.getTimestamp("createdDate"),rs.getInt("countOfAnswer"));
	     	   
	      	});
	    }

	    public Question findById(Long questionId) throws SQLException {
	    	 JdbcTemplate jdbcTemplate = new JdbcTemplate();
	    	
	          	String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS WHERE questionId=?";
	          	return (Question) jdbcTemplate .queryForObject(sql,(ResultSet rs)->{
		      		return new Question(rs.getLong("questionId"),rs.getString("writer"),rs.getString("title"),rs.getString("contents"),rs.getTimestamp("createdDate"),rs.getInt("countOfAnswer"));},questionId);
	          	
	    }

		public void insert(Question question) {
			JdbcTemplate jdbcTemplate = new JdbcTemplate();
			
			String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) VALUES (?, ?, ?, ?, ?)";
			jdbcTemplate.update( sql, question.getWriter(),question.getTitle(),question.getContents(),question.getCreateDate(),question.getCountOfAnswer());
			
		}
		public void delete(Long questionId) {
			JdbcTemplate jdbcTemplate =new JdbcTemplate();
			
			String sql ="DELETE FROM QUESTIONS WHERE questionId=?";
			jdbcTemplate.update(sql,questionId);
		}
		
		public void update(Question question,Long questionId) {
			JdbcTemplate jdbcTemplate = new JdbcTemplate();
			
			String sql = "UPDATE QUESTIONS set title = ?, contents = ? WHERE questionId = ?";
	        jdbcTemplate.update(sql, question.getTitle(), question.getContents(), question.getQuestionId());
		}
		
}