package next.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.db.DataBase;

public class CreateUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = new User(req.getParameter(
                "userId"),
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email"));
        log.debug("User : {}", user);

        DataBase.addUser(user);
        return "redirect:/";
    }
}