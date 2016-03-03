package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class ShowController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao();
	private AnswerDao answerDao = new AnswerDao();
	
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
		Long questionId = Long.parseLong(req.getParameter("questionId"));
		
		ModelAndView mav = jspView("/qna/show.jsp");
		mav.addObject("question", questionDao.findById(questionId));
		mav.addObject("answers", answerDao.findAllByQuestionId(questionId));
		return mav;
	}
}
