package next.controller;

import core.mvc.ModelAndView;
import next.dao.QuestionDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController extends AbstractController {

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) {
        QuestionDao questionDao = new QuestionDao();
        req.setAttribute("questions", questionDao.findAll());
        return jspView("home.jsp")
                .addObject("questions", questionDao.findAll());
    }
}
