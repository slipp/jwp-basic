package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.JdbcQuestionDao;
import next.dao.QuestionDao;

public class ApiListQuestionController extends AbstractController {
    private QuestionDao questionDao;

    public ApiListQuestionController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return jsonView().addObject("questions", questionDao.findAll());
    }
}
