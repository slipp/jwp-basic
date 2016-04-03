package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

public class AddAnswerController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

	private AnswerDao answerDao = AnswerDao.getInstance();
	private QuestionDao questionDao = QuestionDao.getInstance();

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
		Answer answer = new Answer(req.getParameter("writer"), 
				req.getParameter("contents"), 
				Long.parseLong(req.getParameter("questionId")));
		log.debug("answer : {}", answer);
		Question question = questionDao.findById(Long.parseLong(req.getParameter("questionId")));
		Integer countOfComment = question.getCountOfComment() + 1;

		long questionId = question.getQuestionId();
		questionDao.addCountOfAnswer(questionId, countOfComment);
		
		Answer savedAnswer = answerDao.insert(answer);
		return jsonView().addObject("answer", savedAnswer).addObject("questionId", question.getQuestionId());
	}
}
