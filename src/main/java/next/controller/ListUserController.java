package next.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.controller.Controller;
import core.db.DataBase;

public class ListUserController implements Controller {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(!UserSessionUtils.isLogined(request.getSession())) {
			return "redirect:/users/loginForm";
		}
		
		request.setAttribute("users", DataBase.findAll());
		return "/user/list.jsp";
	}

}
