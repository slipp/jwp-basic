package next.controller.qna;

import org.springframework.stereotype.Controller;

@Controller
public class ApiQuestionController {
//	private Logger log = LoggerFactory.getLogger(ApiQuestionController.class);
//	
//	private QnaService qnaService;
//    private QuestionDao questionDao;
//	private AnswerDao answerDao;
//
//	@Inject
//	public ApiQuestionController(QnaService qnaService, QuestionDao questionDao, AnswerDao answerDao) {
//		this.qnaService = qnaService;
//    	this.questionDao = questionDao;
//    	this.answerDao = answerDao;
//    }
//	
//	@RequestMapping(value="/api/qna/deleteQuestion", method=RequestMethod.POST)
//	public ModelAndView deleteQuestion(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//    	if (!UserSessionUtils.isLogined(req.getSession())) {
//			return jsonView().addObject("result", Result.fail("Login is required"));
//		}
//		
//		long questionId = Long.parseLong(req.getParameter("questionId"));
//		try {
//			qnaService.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(req.getSession()));
//			return jsonView().addObject("result", Result.ok());
//		} catch (CannotDeleteException e) {
//			return jsonView().addObject("result", Result.fail(e.getMessage()));
//		}
//	}
//	
//	@RequestMapping("/api/qna/list")
//	public ModelAndView list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//		return jsonView().addObject("questions", questionDao.findAll());
//	}
//	
//	@RequestMapping(value = "/api/qna/addAnswer", method = RequestMethod.POST)
//	public ModelAndView addAnswer(HttpServletRequest req, HttpServletResponse response) throws Exception {
//    	if (!UserSessionUtils.isLogined(req.getSession())) {
//			return jsonView().addObject("result", Result.fail("Login is required"));
//		}
//    	
//    	User user = UserSessionUtils.getUserFromSession(req.getSession());
//		Answer answer = new Answer(user.getUserId(), 
//				req.getParameter("contents"), 
//				Long.parseLong(req.getParameter("questionId")));
//		log.debug("answer : {}", answer);
//		
//		Answer savedAnswer = answerDao.insert(answer);
//		questionDao.updateCountOfAnswer(savedAnswer.getQuestionId());
//		
//		return jsonView().addObject("answer", savedAnswer).addObject("result", Result.ok());
//	}
//	
//	@RequestMapping(value = "/api/qna/deleteAnswer", method = RequestMethod.POST)
//	public ModelAndView deleteAnswer(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		Long answerId = Long.parseLong(request.getParameter("answerId"));
//        
//		ModelAndView mav = jsonView();
//		try {
//			answerDao.delete(answerId);
//			mav.addObject("result", Result.ok());
//		} catch (DataAccessException e) {
//			mav.addObject("result", Result.fail(e.getMessage()));
//		}
//		return mav;
//	}
}
