package next.controller.api;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ApiDeleteQuestionController extends AbstractController {
    private QuestionDao questionDao = QuestionDao.getQuestionDao();
    private AnswerDao answerDao = AnswerDao.getAnswerDao();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jsonView().addObject("result", Result.fail("Login is required"));
        }

        long questionId = Long.parseLong(req.getParameter("questionId"));
        Question question = questionDao.findById(questionId);
        if (question == null) {
            return jsonView().addObject("result", Result.fail("존재하지 않는 질문입니다."));
        }

        if (!question.isSameUser(UserSessionUtils.getUserFromSession(req.getSession()))) {
            return jsonView().addObject("result", Result.fail("다른 사용자가 쓴 글을 삭제할 수 없습니다."));
        }

        List<Answer> answers = answerDao.findAllByQuestionId(questionId);
        if (answers.isEmpty()) {
            questionDao.delete(questionId);
            return jsonView().addObject("result", Result.ok());
        }

        boolean canDelete = true;
        for (Answer answer : answers) {
            String writer = question.getWriter();
            if (!writer.equals(answer.getWriter())) {
                canDelete = false;
                break;
            }
        }

        if (canDelete) {
            questionDao.delete(questionId);
            return jsonView().addObject("result", Result.ok());
        } else {
            return jsonView().addObject("result", Result.fail("다른 사용자가 추가한 댓글이 존재해 삭제할 수 없습니다."));
        }
    }
}