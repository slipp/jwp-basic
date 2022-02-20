package next.controller.user;

import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.nmvc.AbstractNewController;
import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class UserController extends AbstractNewController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private UserDao userDao = UserDao.getInstance();

    @RequestMapping("/users")
    public ModelAndView list(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        ModelAndView mav = jspView("/user/list.jsp");
        mav.addObject("users", userDao.findAll());
        return mav;
    }

    @RequestMapping("/users/form")
    public ModelAndView form(HttpServletRequest req, HttpServletResponse resp) {
        return jspView("/user/form.jsp");
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp) {
        User user = new User(
                req.getParameter("userId"),
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email")
        );
        log.debug("User : {}", user);
        userDao.insert(user);
        return jspView("redirect:/");
    }
}
