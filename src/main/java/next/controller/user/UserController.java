package next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.ModelAndView;
import core.nmvc.AbstractNewController;

@Controller
public class UserController extends AbstractNewController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserDao userDao = UserDao.getInstance();
	
    @RequestMapping("/users")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	if (!UserSessionUtils.isLogined(request.getSession())) {
			return jspView("redirect:/users/loginForm");
		}
    	
        ModelAndView mav = jspView("/user/list.jsp");
        mav.addObject("users", userDao.findAll());
        return mav;
    }
    
    @RequestMapping("/users/form")
    public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return jspView("/user/form.jsp");
    }
    
    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = new User(
				request.getParameter("userId"), 
				request.getParameter("password"), 
				request.getParameter("name"),
				request.getParameter("email"));
        log.debug("User : {}", user);
        userDao.insert(user);
		return jspView("redirect:/");
	}
}
