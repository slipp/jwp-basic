package next.controller.qna;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

public class DeleteQuestionController implements Controller {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (!UserSessionUtils.isLogined(req.getSession())) {
            return "redirect:/users/loginForm";
        }
		
		Long questionId = Long.parseLong(req.getParameter("questionId"));
		QuestionDao questionDao = new QuestionDao();
		
		Question question = questionDao.findById(questionId);
	        if (!question.isSameUser(UserSessionUtils.getUserFromSession(req.getSession()))) {
	            throw new IllegalStateException("다른 사용자가 작성한 게시글을 삭제 할 수 없습니다.");
	        }
			
		
		
		AnswerDao answerDao = new AnswerDao();
		List<Answer> answers = answerDao.findAll(questionId);
		for(Answer answer : answers) {
			answerDao.delete(answer.getAnswerId());
		}
		
		
		questionDao.delete(questionId);
		
		return "redirect:/";
	}

}
