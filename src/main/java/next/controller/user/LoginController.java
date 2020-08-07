package next.controller.user;

import core.mvc.ModelAndView;
import next.controller.AbstractController;
import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginController extends AbstractController {

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);
        if (user == null) {
            return jspView("/user/login.jsp")
                    .addObject("loginFailed", true);
        }
        if (user.matchPassword(password)) {
            HttpSession session = req.getSession();
            return jspView("redirect:/")
                    .addObject(UserSessionUtils.USER_SESSION_KEY, user);
        } else {
            return jspView("/user/login.jsp")
                    .addObject("loginFailed", true);
        }
    }
}
