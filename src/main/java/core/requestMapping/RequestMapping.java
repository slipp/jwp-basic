package core.requestMapping;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.controller.Controller;
import next.controller.HomeController;
import next.controller.ListUserController;

public class RequestMapping{
	private static Map<String, Controller> controllers = new LinkedHashMap<>();
	
	static {
		controllers.put("/user/list", new ListUserController());
		controllers.put("/", new HomeController());
		controllers.put("/users/LoginForm", new HomeController());
		controllers.put("/users/login", new HomeController());
	}
	
	public static String getController(String requestUrl, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		return controllers.get(requestUrl).execute(req, resp);
	}
	
}
