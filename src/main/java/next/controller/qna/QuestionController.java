package next.controller.qna;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import core.web.argumentresolver.LoginUser;
import next.CannotDeleteException;
import next.model.Question;
import next.model.User;
import next.service.QnaService;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	private QnaService qnaService;

	@Inject
	public QuestionController(QnaService qnaService) {
		this.qnaService = qnaService;
	}

	@RequestMapping(value = "/{questionId}", method = RequestMethod.GET)
	public String show(@PathVariable Long questionId, Model model) throws Exception {
		Question question = qnaService.findById(questionId);
		model.addAttribute("question", question);
		return "/qna/show";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String createForm(@LoginUser User loginUser, Model model) throws Exception {
		model.addAttribute("question", new Question());
		return "/qna/form";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String create(@LoginUser User loginUser, Question question) throws Exception {
		qnaService.create(question, loginUser);
		return "redirect:/";
	}

	@RequestMapping(value = "/{questionId}/edit", method = RequestMethod.GET)
	public String editForm(@LoginUser User loginUser, @PathVariable Long questionId, Model model) throws Exception {
		Question question = qnaService.findById(questionId);
		if (!question.isSameWriter(loginUser)) {
			throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
		}
		model.addAttribute("question", question);
		return "/qna/update.jsp";
	}

	@RequestMapping(value = "/{questionId}", method = RequestMethod.PUT)
	public String edit(@LoginUser User loginUser, @PathVariable Long questionId, Question editQuestion) throws Exception {
		qnaService.update(editQuestion, loginUser);
		return "redirect:/";
	}

	@RequestMapping(value = "/{questionId}", method = RequestMethod.DELETE)
	public String delete(@LoginUser User loginUser, @PathVariable Long questionId, Model model) throws Exception {
		try {
			qnaService.deleteQuestion(questionId, loginUser);
			return "redirect:/";
		} catch (CannotDeleteException e) {
			model.addAttribute("question", qnaService.findById(questionId));
			model.addAttribute("errorMessage", e.getMessage());
			return "show.jsp";
		}
	}
}
