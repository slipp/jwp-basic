package next.controller.qna;

import core.mvc.ModelAndView;
import next.controller.AbstractController;
import next.dao.AnswerDao;
import next.dao.QuestionDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowController extends AbstractController {

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) {
        long questionId = Long.parseLong(req.getParameter("questionId"));
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();
        return jspView("/qna/show.jsp")
                .addObject("question", questionDao.findById(questionId))
                .addObject("answers", answerDao.findAllByQuestionId(questionId));
    }
}
