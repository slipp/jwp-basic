package next.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.dao.UserDao;
import next.model.User;

@WebServlet("/users/profile")
public class ProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        User user = null;
        UserDao userDao = new UserDao();
    	try {
			user = userDao.findByUserId(userId);
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        req.setAttribute("user", user);
        RequestDispatcher rd = req.getRequestDispatcher("/user/profile.jsp");
        rd.forward(req, resp);
    }
}
