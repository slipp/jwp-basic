package next.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.db.DataBase;
import next.dao.UserDao;
import next.model.User;

@WebServlet(value = { "/user/update", "/user/updateForm" })
public class UpdateUserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        User user = findByUserId(userId);
        req.setAttribute("user", user);
        RequestDispatcher rd = req.getRequestDispatcher("/update.jsp");
        rd.forward(req, resp);
    }
    
    private User findByUserId(String userId) {
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
        return user;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User updateUser = new User(
                req.getParameter("userId"), 
                req.getParameter("password"), 
                req.getParameter("name"),
                req.getParameter("email"));
        System.out.println("User : " + updateUser);
        
        User user = findByUserId(updateUser.getUserId());
        UserDao userDao = new UserDao();
        user.update(updateUser);
        try {
            userDao.update(user);
        } catch (SQLException e) {
        }

        req.setAttribute("users", DataBase.findAll());
        resp.sendRedirect("/");
    }
}
