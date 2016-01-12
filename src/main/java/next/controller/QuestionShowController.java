package next.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;

@WebServlet("/qna/show")
public class QuestionShowController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long questionId = Long.parseLong(req.getParameter("questionId"));
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();
        try {
            req.setAttribute("question", questionDao.findById(questionId));
            req.setAttribute("answers", answerDao.findAllByQuestionId(questionId));
            
            RequestDispatcher rd = req.getRequestDispatcher("/show.jsp");
            rd.forward(req, resp);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
