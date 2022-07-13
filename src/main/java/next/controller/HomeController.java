package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.controller.Controller;
import core.db.DataBase;

public class HomeController implements Controller {
    
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("users", DataBase.findAll());
		log.debug("home에 제대로 왔음?");
		return "index.jsp";
	}
    
  
}
