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

@WebServlet("/users")
public class ListUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(ListUserController.class);

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!UserSessionUtils.isLogined(req.getSession())) {
			resp.sendRedirect("/users/loginForm");
			return;
		}
		
		UserDao userDao = new UserDao();
        try {
        	req.setAttribute("users", userDao.findAll());
		} catch (SQLException e) {
			log.error(e.getMessage());
		}

        RequestDispatcher rd = req.getRequestDispatcher("/user/list.jsp");
        rd.forward(req, resp);
    }
}
