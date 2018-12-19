package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.Controller;
import next.dao.QuestionDao;
import next.model.Question;

public class CreateQuestionController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(CreateQuestionController.class);
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Question question = new Question(req.getParameter("writer"),req.getParameter("title"),req.getParameter("contents"));
		
		QuestionDao questionDao = new QuestionDao();
		questionDao.insert(question);
		
		return "redirect:/";
	}

}
