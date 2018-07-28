package next.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.controller.Controller;
import core.db.DataBase;

@WebServlet("/users")
public class ListUserController implements Controller {
        private static final long serialVersionUID = 1L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return "redirect:/users/loginForm";
        }

        request.setAttribute("users", DataBase.findAll());
        return "/user/list.jsp";
    }
}