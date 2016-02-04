package next.controller;

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

@WebServlet(value = { "/users/update", "/users/updateForm" })
public class UpdateUserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("user") == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        User user = (User)session.getAttribute("user");
        req.setAttribute("user", user);
        RequestDispatcher rd = req.getRequestDispatcher("/user/updateForm.jsp");
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User updateUser = new User(
                req.getParameter("userId"), 
                req.getParameter("password"), 
                req.getParameter("name"),
                req.getParameter("email"));
        System.out.println("User : " + updateUser);

        User user = DataBase.findUserById(updateUser.getUserId());
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        user.update(updateUser);

        req.setAttribute("users", DataBase.findAll());
        resp.sendRedirect("/");
    }
}
