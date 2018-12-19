package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.dao.QuestionDao;
import next.model.Question;

public class UpdateFormQuestionController implements Controller {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		QuestionDao questionDao = new QuestionDao();
		long questionId = Long.parseLong(req.getParameter("questionId"));
        Question question = questionDao.findById(questionId);
        
        req.setAttribute("question", question);
        return "/qna/QuestionRevise.jsp";
	}

}
