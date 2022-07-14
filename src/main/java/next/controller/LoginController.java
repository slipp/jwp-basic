package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.controller.Controller;
import core.db.DataBase;
import next.model.User;

public class LoginController implements Controller {
    
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("로그인 제대로 넘어오는지");
		if(request.getMethod().equals("GET")) return "/user/login.jsp";
		
		String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        User user = DataBase.findUserById(userId);
        if (user == null) {
        	request.setAttribute("loginFailed", true);
            return "/user/login.jsp";
        }

        if (user.matchPassword(password)) {
            HttpSession session = request.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return "redirect:/";
        }
        
        request.setAttribute("loginFailed", true);
        return "/user/login.jsp";
		
	}

    
}
