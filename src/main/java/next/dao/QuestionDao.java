package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.Question;

public class QuestionDao {
    public List<Question> findAll() throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT questionId, writer, title, createdDate, countOfComment FROM QUESTIONS "
                    + "order by questionId desc";
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<Question> questions = new ArrayList<Question>();
            while (rs.next()) {
                questions.add(new Question(rs.getLong("questionId"),
                        rs.getString("writer"), rs.getString("title"), null,
                        rs.getTimestamp("createdDate"),
                        rs.getInt("countOfComment")));
            }

            return questions;
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

    public Question findById(Long questionId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT questionId, writer, title, contents, createdDate, countOfComment FROM QUESTIONS "
                    + "WHERE questionId = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setLong(1, questionId);

            rs = pstmt.executeQuery();

            Question question = null;
            if (rs.next()) {
                question = new Question(rs.getLong("questionId"),
                        rs.getString("writer"), rs.getString("title"),
                        rs.getString("contents"),
                        rs.getTimestamp("createdDate"),
                        rs.getInt("countOfComment"));
            }

            return question;
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
