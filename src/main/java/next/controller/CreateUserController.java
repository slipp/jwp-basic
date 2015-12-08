package next.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.web.AbstractBaseServlet;
import next.dao.UserDao;
import next.model.User;

@WebServlet(value= {"/user", "/user/form"})
public class CreateUserController extends AbstractBaseServlet {
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateUserController.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process("form.ftl", resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = new User(
				req.getParameter("userId"), 
				req.getParameter("password"),
				req.getParameter("name"),
				req.getParameter("email"));
		LOGGER.debug("User : {}", user);
		UserDao userDao = new UserDao();
		try {
			userDao.insert(user);
		} catch (SQLException e) {
		}
		resp.sendRedirect("/");
	}
}
