package next.controller.qna;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import next.dao.AnswerDao;
import next.model.Answer;

@WebServlet("/api/qna/addanswer")
public class AnswerAddController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Answer answer = new Answer(
               req.getParameter("writer"),
               req.getParameter("contents"),
               Long.parseLong(req.getParameter("questionId")));
        System.out.println("Answer : " + answer);
        AnswerDao answerDao = new AnswerDao();
        try {
            Answer savedAnswer = answerDao.addAnswer(answer);
            ObjectMapper mapper = new ObjectMapper();
            resp.setContentType("application/json;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.print(mapper.writeValueAsString(savedAnswer));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
