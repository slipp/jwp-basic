package next.controller.user;

import core.mvc.ModelAndView;
import next.controller.AbstractController;
import next.controller.UserSessionUtils;
import next.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListUserController extends AbstractController {

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        UserDao userDao = new UserDao();
        return jspView("/user/list.jsp")
                .addObject("users", userDao.findAll());
    }
}
