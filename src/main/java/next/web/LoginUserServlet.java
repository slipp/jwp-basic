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
public class LoginUserServlet extends HttpServlet  {

	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = DataBase.findUserById(req.getParameter("userId"));
        if(user.getUserId()==req.getParameter("userId") & user.getPassword()==req.getParameter("password")) {
        	HttpSession session = req.getSession();
        	session.setAttribute("user", user);
        	req.setAttribute("name", user.getName());
            RequestDispatcher rd = req.getRequestDispatcher("/user/index.html");
            rd.forward(req, resp);
        } else {
        	System.out.println("no match");
            RequestDispatcher rd = req.getRequestDispatcher("/user/login_failed.html");
            rd.forward(req, resp);
        }
        
    }
}
