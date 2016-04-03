package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.Result;
import core.jdbc.DataAccessException;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class DeleteAnswerController extends AbstractController {
    private AnswerDao answerDao = AnswerDao.getInstance();
    private QuestionDao questionDao = QuestionDao.getInstance();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long answerId = Long.parseLong(request.getParameter("answerId"));
		Long questionId = Long.parseLong(request.getParameter("questionId"));
		Question question = questionDao.findById(questionId);
		questionDao.delCountOfAnswer(questionId, question.getCountOfComment()-1);
		ModelAndView mav = jsonView();
		try {
			answerDao.delete(answerId);
			mav.addObject("result", Result.ok());
			mav.addObject("answerId", answerId);
		} catch (DataAccessException e) {
			mav.addObject("result", Result.fail(e.getMessage()));
		}
		return mav;
	}
}
