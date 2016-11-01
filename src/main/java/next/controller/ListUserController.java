package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;

public class ListUserController implements Controller {
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		if (!UserSessionUtils.isLogined(req.getSession())) {
			return "redirect:/users/loginForm";
        }
		
		req.setAttribute("users", DataBase.findAll());
		return "/user/list.jsp";
	}
}
