package next.controller.qna;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import next.CannotDeleteException;
import next.controller.UserSessionUtils;
import next.model.Question;
import next.model.User;
import next.service.QnaService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	public String createForm(HttpSession session, Model model) throws Exception {
		if (!UserSessionUtils.isLogined(session)) {
			return "redirect:/users/loginForm";
		}
		model.addAttribute("question", new Question());
		return "/qna/form";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String create(Question question, HttpSession session) throws Exception {
		if (!UserSessionUtils.isLogined(session)) {
			return "redirect:/users/loginForm";
		}

		User user = UserSessionUtils.getUserFromSession(session);
		qnaService.create(question, user);
		return "redirect:/";
	}

	@RequestMapping(value = "/{questionId}/edit", method = RequestMethod.GET)
	public String editForm(@PathVariable Long questionId, HttpSession session, Model model) throws Exception {
		if (!UserSessionUtils.isLogined(session)) {
			return "redirect:/users/loginForm";
		}
		Question question = qnaService.findById(questionId);
		if (!question.isSameWriter(UserSessionUtils.getUserFromSession(session))) {
			throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
		}
		model.addAttribute("question", question);
		return "/qna/update.jsp";
	}

	@RequestMapping(value = "/{questionId}", method = RequestMethod.PUT)
	public String edit(@PathVariable Long questionId, Question editQuestion, HttpSession session) throws Exception {
		if (!UserSessionUtils.isLogined(session)) {
			return "redirect:/users/loginForm";
		}

		qnaService.update(editQuestion, UserSessionUtils.getUserFromSession(session));
		return "redirect:/";
	}

	@RequestMapping(value = "/{questionId}", method = RequestMethod.DELETE)
	public String delete(@PathVariable Long questionId, HttpSession session, Model model) throws Exception {
		if (!UserSessionUtils.isLogined(session)) {
			return "redirect:/users/loginForm";
		}

		try {
			qnaService.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(session));
			return "redirect:/";
		} catch (CannotDeleteException e) {
			model.addAttribute("question", qnaService.findById(questionId));
			model.addAttribute("errorMessage", e.getMessage());
			return "show.jsp";
		}
	}
}
