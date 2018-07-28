package next.controller;

import core.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;

@WebServlet("/users/logout")
public class LogoutController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(LogoutController.class);

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        resp.sendRedirect("/");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }
}