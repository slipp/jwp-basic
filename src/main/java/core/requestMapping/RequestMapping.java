package core.requestMapping;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.controller.Controller;
import next.controller.CreateUserController;
import next.controller.HomeController;
import next.controller.ListUserController;
import next.controller.LoginController;
import next.controller.LogoutController;
import next.controller.ProfileController;
import next.controller.UpdateUserController;

public class RequestMapping{
	private static Map<String, Controller> controllers = new LinkedHashMap<>();
	
	
	private static final Logger log = LoggerFactory.getLogger(RequestMapping.class);
	
	static {
		controllers.put("/user/list", new ListUserController());
		controllers.put("", new HomeController());
		controllers.put("/", new HomeController());
		controllers.put("/users/loginForm", new LoginController());
		controllers.put("/users/login", new LoginController());
		controllers.put("/users/create", new CreateUserController());
		controllers.put("/users/form", new CreateUserController());
		controllers.put("/users/logout", new LogoutController());
		controllers.put("/users/profile", new ProfileController());
		controllers.put("/users/update", new UpdateUserController());
		controllers.put("/users/updateForm", new UpdateUserController());
	}
	
	public static String getController(String requestUrl, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		log.debug("{}",controllers.get(requestUrl).getClass().getName());
		return controllers.get(requestUrl).execute(req, resp);
	}
	
}
