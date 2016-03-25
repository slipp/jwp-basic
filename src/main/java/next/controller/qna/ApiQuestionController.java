package next.controller.qna;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import core.web.argumentresolver.LoginUser;
import next.CannotDeleteException;
import next.model.Question;
import next.model.Result;
import next.model.User;
import next.service.QnaService;

@RestController
@RequestMapping("/api")
public class ApiQuestionController {
	private Logger log = LoggerFactory.getLogger(ApiQuestionController.class);
	
	private QnaService qnaService;

	@Inject
	public ApiQuestionController(QnaService qnaService) {
		this.qnaService = qnaService;
    }
	
	@RequestMapping(value="/questions/{questionId}", method=RequestMethod.DELETE)
	public Result deleteQuestion(@LoginUser User loginUser, @PathVariable Long questionId) throws Exception {
		try {
			qnaService.deleteQuestion(questionId, loginUser);
			return Result.ok();
		} catch (CannotDeleteException e) {
			return Result.fail(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/questions", method = RequestMethod.GET)
	public List<Question> list() throws Exception {
		return qnaService.findQuestions();
	}
	
	@RequestMapping(value = "/questions/{questionId}/answers", method = RequestMethod.POST)
	public Model addAnswer(@LoginUser User loginUser, @PathVariable Long questionId, String contents, Model model) throws Exception {
		log.debug("questionId : {}, contents : {}", questionId, contents);
		
		model.addAttribute("answer", qnaService.addAnswer(loginUser, questionId, contents));
		model.addAttribute("result", Result.ok());
		return model;
	}
	
	@RequestMapping(value = "/questions/{questionId}/answers/{answerId}", method = RequestMethod.DELETE)
	public Result deleteAnswer(@LoginUser User loginUser, @PathVariable Long answerId) throws Exception {
		qnaService.deleteAnswer(answerId, loginUser);
		return Result.ok();
	}
}
