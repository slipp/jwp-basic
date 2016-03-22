package next.controller.qna;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import next.controller.UserSessionUtils;
import next.model.Question;
import next.service.QnaService;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	private QnaService qnaService;
//    private QuestionDao questionDao;
//	private AnswerDao answerDao;
//
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
	
	@RequestMapping("/form")
	public String createForm(HttpSession session, Model model) throws Exception {
		if (!UserSessionUtils.isLogined(session)) {
			return "redirect:/users/loginForm";
		}
		model.addAttribute("question", new Question());
		return "/qna/form";
	}
	
//	@RequestMapping(value="/qna/create", method=RequestMethod.POST)
//	public ModelAndView create(HttpServletRequest request, HttpServletResponse response) throws Exception {
//    	if (!UserSessionUtils.isLogined(request.getSession())) {
//			return jspView("redirect:/users/loginForm");
//		}
//    	User user = UserSessionUtils.getUserFromSession(request.getSession());
//    	Question question = new Question(user.getUserId(), request.getParameter("title"), request.getParameter("contents"));
//    	questionDao.insert(question);
//		return jspView("redirect:/");
//	}
//	
//	@RequestMapping("/qna/updateForm")
//	public ModelAndView updateForm(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//    	if (!UserSessionUtils.isLogined(req.getSession())) {
//			return jspView("redirect:/users/loginForm");
//		}
//		
//		long questionId = Long.parseLong(req.getParameter("questionId"));
//		Question question = questionDao.findById(questionId);
//		if (!question.isSameWriter(UserSessionUtils.getUserFromSession(req.getSession()))) {
//			throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
//		}
//		return jspView("/qna/update.jsp").addObject("question", question);
//	}
//	
//	@RequestMapping(value="/qna/update", method=RequestMethod.POST)
//	public ModelAndView update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//    	if (!UserSessionUtils.isLogined(req.getSession())) {
//			return jspView("redirect:/users/loginForm");
//		}
//		
//		long questionId = Long.parseLong(req.getParameter("questionId"));
//		Question question = questionDao.findById(questionId);
//		if (!question.isSameWriter(UserSessionUtils.getUserFromSession(req.getSession()))) {
//			throw new IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.");
//		}
//		
//		Question newQuestion = new Question(question.getWriter(), req.getParameter("title"), req.getParameter("contents"));
//		question.update(newQuestion);
//		questionDao.update(question);
//		return jspView("redirect:/");
//	}
//	
//	@RequestMapping(value="/qna/delete", method=RequestMethod.POST)
//	public ModelAndView delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//    	if (!UserSessionUtils.isLogined(req.getSession())) {
//			return jspView("redirect:/users/loginForm");
//		}
//		
//		long questionId = Long.parseLong(req.getParameter("questionId"));
//		try {
//			qnaService.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(req.getSession()));
//			return jspView("redirect:/");
//		} catch (CannotDeleteException e) {
//			return jspView("show.jsp")
//					.addObject("question", qnaService.findById(questionId))
//					.addObject("answers", qnaService.findAllByQuestionId(questionId))
//					.addObject("errorMessage", e.getMessage());
//		}
//	}
}
