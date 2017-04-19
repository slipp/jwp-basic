package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Jbee on 2017. 4. 19..
 */
public class QuestionController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        if (!UserSessionUtils.isLogined(session)) {
            return jspView("redirect:/users/loginForm");
        }

        User user = UserSessionUtils.getUserFromSession(session);
        ModelAndView mav = jspView("/qna/form.jsp");
        mav.addObject("user", user);
        return mav;
    }
}
