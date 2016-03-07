package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.Result;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class ApiDeleteQuestionController extends AbstractController {
	private QuestionDao questionDao = QuestionDao.getInstance();
	
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	if (!UserSessionUtils.isLogined(req.getSession())) {
			return jsonView().addObject("result", Result.fail("Login is required"));
		}
		
		long questionId = Long.parseLong(req.getParameter("questionId"));
		Question question = questionDao.findById(questionId);
		if (!question.isSameUser(UserSessionUtils.getUserFromSession(req.getSession()))) {
			return jsonView().addObject("result", Result.fail("다른 사용자가 쓴 글을 삭제할 수 없습니다."));
		}
		
		return jsonView();
	}
}