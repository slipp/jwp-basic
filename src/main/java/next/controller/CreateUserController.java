package next.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.controller.Controller;
import next.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;

@WebServlet(value = { "/users/create", "/users/form" })
public class CreateUserController implements Controller {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/user/form.jsp");
        rd.forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
        log.debug("user : {}", user);
        DataBase.addUser(user);
        resp.sendRedirect("/");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }
}