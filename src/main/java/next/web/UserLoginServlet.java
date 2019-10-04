package next.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.db.DataBase;
import next.model.User;

@WebServlet("/user/login")
public class UserLoginServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		forward(req, resp, "/user/login.jsp");
		//resp.sendRedirect("/user/login.jsp");
		
		
				
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("userId");
		String pw = req.getParameter("password");
		User user = DataBase.findUserById(id);
		if (!user.getPassword().equals(pw)) {
			throw new IllegalStateException("아이디 또는 패스워드가 틀렸습니다.");
		}
		HttpSession session = req.getSession();
		session.setAttribute("user", user);
		resp.sendRedirect("/");
		
		
	}
	
	private void forward(HttpServletRequest req, HttpServletResponse resp, String path) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher(path);
		rd.forward(req, resp);
	}
}
