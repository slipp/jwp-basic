package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;

public class ShowController implements Controller {
	private final static Logger log = LoggerFactory.getLogger(ShowController.class);
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		log.debug("questionId={}",req.getParameter("questionId"));
		log.debug("answerId={}",req.getParameter("answerId"));
		Long questionId = Long.parseLong(req.getParameter("questionId"));
		QuestionDao questionDao = new QuestionDao();
		AnswerDao answerDao = new AnswerDao();
		
		req.setAttribute("question", questionDao.findById(questionId));
		req.setAttribute("answers", answerDao.findAll(questionId));
		
		return "/qna/show.jsp";
	}

}
