package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.Answer;

public class AnswerDao {
    public List<Answer> findAllByQuestionId(long questionId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT answerId, writer, contents, createdDate FROM ANSWERS WHERE questionId = ? "
                    + "order by answerId desc";
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, questionId);

            rs = pstmt.executeQuery();

            List<Answer> answers = new ArrayList<Answer>();
            while (rs.next()) {
                answers.add(new Answer(
                        rs.getLong("answerId"),
                        rs.getString("writer"), 
                        rs.getString("contents"),
                        rs.getTimestamp("createdDate"), 
                        questionId));
            }

            return answers;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public Answer findByAnswerId(long answerId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, answerId);

            rs = pstmt.executeQuery();

            Answer answer = null;
            if (rs.next()) {
                answer = new Answer(
                        rs.getLong("answerId"),
                        rs.getString("writer"), 
                        rs.getString("contents"),
                        rs.getTimestamp("createdDate"), 
                        rs.getLong("questionId"));
            }

            return answer;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    private Long newAnswerId() throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT max(answerId) as answerId FROM ANSWERS";
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            Long answerId = 0L;
            if (rs.next()) {
                answerId = rs.getLong("answerId");
            }

            return answerId + 1;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public Answer addAnswer(Answer answer) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            
            Long answerId = newAnswerId();
            String sql = "INSERT INTO ANSWERS (answerId, writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, answerId);
            pstmt.setString(2, answer.getWriter());
            pstmt.setString(3, answer.getContents());
            pstmt.setTimestamp(4, new Timestamp(answer.getTimeFromCreateDate()));
            pstmt.setLong(5, answer.getQuestionId());
            pstmt.executeUpdate();
            
            return findByAnswerId(answerId);
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
        
    }

    public void delete(Long answerId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "DELETE FROM ANSWERS WHERE answerId = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, answerId);

            pstmt.executeUpdate();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
