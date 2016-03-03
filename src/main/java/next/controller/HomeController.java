package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

public class HomeController extends AbstractController {
    private QuestionDao questionDao = new QuestionDao();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mav = jspView("index.jsp");
        mav.addObject("questions", questionDao.findAll());
        return mav;
    }
}
