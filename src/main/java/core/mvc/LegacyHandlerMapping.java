package core.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import next.controller.qna.AddAnswerController;
import next.controller.qna.ApiDeleteQuestionController;
import next.controller.qna.ApiListQuestionController;
import next.controller.qna.CreateFormQuestionController;
import next.controller.qna.CreateQuestionController;
import next.controller.qna.DeleteAnswerController;
import next.controller.qna.DeleteQuestionController;
import next.controller.qna.ShowQuestionController;
import next.controller.qna.UpdateFormQuestionController;
import next.controller.qna.UpdateQuestionController;
import next.controller.user.LoginController;
import next.controller.user.LogoutController;
import next.controller.user.ProfileController;
import next.controller.user.UpdateFormUserController;
import next.controller.user.UpdateUserController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.nmvc.HandlerMapping;

public class LegacyHandlerMapping implements HandlerMapping {
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
	private Map<String, Controller> mappings = new HashMap<>();
	
	void initMapping() {
	    mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
		mappings.put("/users/login", new LoginController());
		mappings.put("/users/profile", new ProfileController());
	    mappings.put("/users/logout", new LogoutController());
	    mappings.put("/users/updateForm", new UpdateFormUserController());
	    mappings.put("/users/update", new UpdateUserController());
		mappings.put("/qna/show", new ShowQuestionController());
		mappings.put("/qna/form", new CreateFormQuestionController());
		mappings.put("/qna/create", new CreateQuestionController());
		mappings.put("/qna/updateForm", new UpdateFormQuestionController());
		mappings.put("/qna/update", new UpdateQuestionController());
		mappings.put("/qna/delete", new DeleteQuestionController());
		mappings.put("/api/qna/deleteQuestion", new ApiDeleteQuestionController());
		mappings.put("/api/qna/list", new ApiListQuestionController());
		mappings.put("/api/qna/addAnswer", new AddAnswerController());
		mappings.put("/api/qna/deleteAnswer", new DeleteAnswerController());

		logger.info("Initialized Request Mapping!");
	}
	
	public Controller findController(String url) {
		return mappings.get(url);
	}
	
	void put(String url, Controller controller) {
		mappings.put(url, controller);
	}

	@Override
	public Controller getHandler(HttpServletRequest request) {
		return mappings.get(request.getRequestURI());
	}
}
