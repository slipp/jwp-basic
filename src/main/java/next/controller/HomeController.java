package next.controller;

import core.mvc.Controller;
import core.mvc.JspView;
import core.mvc.View;
import next.dao.QuestionDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController implements Controller {

    @Override
    public View execute(HttpServletRequest req, HttpServletResponse resp) {
        QuestionDao questionDao = new QuestionDao();
        req.setAttribute("questions", questionDao.findAll());
        return new JspView("home.jsp");
    }
}
