package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;

import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import next.controller.qna.DeleteAnswerController;
import next.model.Answer;
import next.model.Question;

public class AnswerDao {
	private final static Logger log = LoggerFactory.getLogger(DeleteAnswerController.class);
		public List<Answer> findAll(long questionId) throws SQLException {
	      
	    	JdbcTemplate jdbcTemplate = new JdbcTemplate();
	    	
	        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE questionId=? ORDER BY answerId DESC";
	        return jdbcTemplate.query(sql,(ResultSet rs)->{
	      		return new Answer(rs.getLong("answerId"),rs.getString("writer"),rs.getString("contents"),rs.getTimestamp("createdDate"),rs.getLong("questionId"));
	     	   
	      	},questionId);
	    }

	    public Answer findById(long answerId) throws SQLException {
	    	 JdbcTemplate jdbcTemplate = new JdbcTemplate();
	    	
	          	String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId=?";
	          	return jdbcTemplate .queryForObject(sql,(ResultSet rs)->{
		      		return new Answer(rs.getLong("answerId"),rs.getString("writer"),rs.getString("contents"),rs.getTimestamp("createdDate"),rs.getLong("questionId"));},answerId);
	          	
	    }
	    public void delete(Long answerId) {
			JdbcTemplate jdbcTemplate =new JdbcTemplate();
			
			String sql ="DELETE FROM ANSWERS WHERE answerId=?";
			jdbcTemplate.update(sql,answerId);
		}

	    public Answer insert(Answer answer) throws SQLException {
	    	JdbcTemplate jdbcTemplate = new JdbcTemplate();
	    	String sql ="INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";
			
	    	PreparedStatementCreator psc = new PreparedStatementCreator() {

				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement pstmt = con.prepareStatement(sql);
					pstmt.setString(1, answer.getWriter());
					pstmt.setString(2, answer.getContents());
					pstmt.setTimestamp(3, new Timestamp(answer.getTimeFromCreatedDate()));
					pstmt.setLong(4, answer.getQuestionId());
					return pstmt;
				}
	    		
	    	};
	    	KeyHolder holder = new KeyHolder();
	    	jdbcTemplate.update(psc, holder);
	    	return findById(holder.getId());
	    	
	    }
	    public void update(Answer answer,Long answerId) {
	    	
			JdbcTemplate jdbcTemplate = new JdbcTemplate();
			
			String sql = "UPDATE ANSWERS set contents = ? WHERE answerId = ?";
	        jdbcTemplate.update(sql, answer.getContents(), answer.getAnswerId());
		}
}
