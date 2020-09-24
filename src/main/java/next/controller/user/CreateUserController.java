package next.controller.user;

import core.mvc.ModelAndView;
import next.controller.AbstractController;
import next.dao.UserDao;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateUserController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) {
        // TODO: User에 Builder 패턴 적용, 아래에서 사용
        User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
        log.debug("User : {}", user);

        UserDao userDao = new UserDao();
        userDao.insert(user);
        return jspView("redirect:/");
    }
}
