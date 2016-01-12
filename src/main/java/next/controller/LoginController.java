package next.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import next.dao.UserDao;
import next.model.User;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        
        UserDao userDao = new UserDao();
        User user = null;
        try {
            user = userDao.findByUserId(userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        
        if (user.matchPassword(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect("/");
        } else {
            throw new IllegalStateException("비밀번호가 틀립니다.");
        }
    }
}
