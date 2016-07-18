package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.web.mvc.AbstractNewController;
import core.web.view.ModelAndView;
import next.dao.QuestionDao;

@Controller
public class HomeController extends AbstractNewController {
    private QuestionDao questionDao;

    @Inject
    public HomeController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @RequestMapping("/")
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return jspView("home.jsp").addObject("questions", questionDao.findAll());
    }
}