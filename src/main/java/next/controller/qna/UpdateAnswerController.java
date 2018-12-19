package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.Answer;

public class UpdateAnswerController implements Controller {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		AnswerDao answerDao = new AnswerDao();
		System.out.println(req.getParameter("answerId"));
        long answerId = Long.parseLong(req.getParameter("answerId"));

		System.out.println("11111111111111111111");
        Answer answer = answerDao.findById(answerId);
        System.out.println("11111111111111111111");

        Answer newAnswer = new Answer( answer.getWriter(), req.getParameter("contents"),answer.getAnswerId());
        answer.update(newAnswer);
        answerDao.update(answer,answerId);
        
        return "redirect:/";
	}
	
}
