package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.Answer;

public class UpdateFormAnswerController implements Controller {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		AnswerDao answerDao = new AnswerDao();
		long answerId = Long.parseLong(req.getParameter("answerId"));
        Answer answer = answerDao.findById(answerId);
        
        req.setAttribute("answer", answer);
        
        return "/qna/AnswerRevise.jsp";
	}

}
