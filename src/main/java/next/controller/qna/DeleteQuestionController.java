package next.controller.qna;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class DeleteQuestionController extends AbstractController {
	private QuestionDao questionDao = QuestionDao.getInstance();
	private AnswerDao answerDao = AnswerDao.getInstance();
	
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	if (!UserSessionUtils.isLogined(req.getSession())) {
			return jspView("redirect:/users/loginForm");
		}
		
		long questionId = Long.parseLong(req.getParameter("questionId"));
		Question question = questionDao.findById(questionId);
		if (question == null) {
			throw new IllegalStateException("존재하지 않는 질문입니다.");
		}
		
		if (!question.isSameUser(UserSessionUtils.getUserFromSession(req.getSession()))) {
			return createModelAndView(question, answerDao.findAllByQuestionId(questionId), "다른 사용자가 쓴 글을 삭제할 수 없습니다.");
		}
		
		List<Answer> answers = answerDao.findAllByQuestionId(questionId);
		if (answers.isEmpty()) {
			questionDao.delete(questionId);
			return jspView("redirect:/");
		}
		
		boolean canDelete = true;
		for (Answer answer : answers) {
			String writer = question.getWriter();
			if (!writer.equals(answer.getWriter())) {
				canDelete = false;
				break;
			}
		}
		
		if (canDelete) {
			questionDao.delete(questionId);
			return jspView("redirect:/");
		}
		
		return createModelAndView(question, answers, "다른 사용자가 추가한 댓글이 존재해 삭제할 수 없습니다.");
	}
	
	private ModelAndView createModelAndView(Question question, List<Answer> answers, String errorMessage) {
		return jspView("show.jsp")
				.addObject("question", question)
				.addObject("answers", answers)
				.addObject("errorMessage", "다른 사용자가 추가한 댓글이 존재해 삭제할 수 없습니다.");
	}
}
