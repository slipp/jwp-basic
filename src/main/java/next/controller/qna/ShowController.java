package next.controller.qna;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class ShowController extends AbstractController {
	private QuestionDao questionDao = QuestionDao.getInstance();
	private AnswerDao answerDao = AnswerDao.getInstance();
	
	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
		Question question;
		List<Answer> answers; // heap에 저장이 되면 자원이 공유되므로, stack영역의 지역변수로 선언하여 상태를 공유할 수 없도록 메서드 안으로 옮겨준다. 
		Long questionId = Long.parseLong(req.getParameter("questionId"));
		question = questionDao.findById(questionId);
		int countOfAnswer = question.getCountOfComment() - 1;
		if(countOfAnswer < 0){
			countOfAnswer = 0;
		}
		answers = answerDao.findAllByQuestionId(questionId);
		ModelAndView mav = jspView("/qna/show.jsp");
		mav.addObject("question", question);
		mav.addObject("answers", answers);
		mav.addObject("countOfAnswer", countOfAnswer);
		return mav;
	}
}
