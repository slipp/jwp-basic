package next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.ModelAndView;
import core.nmvc.AbstractNewController;
import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.model.User;

@Controller
public class UserController extends AbstractNewController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserDao userDao;

    @Inject
    public UserController(UserDao userDao) {
		this.userDao = userDao;
	}

	@RequestMapping("/users")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	if (!UserSessionUtils.isLogined(request.getSession())) {
			return jspView("redirect:/users/loginForm");
		}
    	
        ModelAndView mav = jspView("/user/list.jsp");
        mav.addObject("users", userDao.findAll());
        return mav;
    }
    
    @RequestMapping("/users/profile")
    public ModelAndView profile(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        ModelAndView mav = jspView("/user/profile.jsp");
        mav.addObject("user", userDao.findByUserId(userId));
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
    
    @RequestMapping("/users/updateForm")
	public ModelAndView updateForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	User user = userDao.findByUserId(request.getParameter("userId"));

    	if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
        	throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
    	ModelAndView mav = jspView("/user/updateForm.jsp");
    	mav.addObject("user", user);
    	return mav;
	}
    
    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest req, HttpServletResponse response) throws Exception {
		User user = userDao.findByUserId(req.getParameter("userId"));
		
        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
        	throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        
        User updateUser = new User(
                req.getParameter("userId"), 
                req.getParameter("password"), 
                req.getParameter("name"),
                req.getParameter("email"));
        log.debug("Update User : {}", updateUser);
        user.update(updateUser);
        return jspView("redirect:/");
	}
    
    @RequestMapping("/users/loginForm")
    public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return jspView("/user/login.jsp");
    }
    
    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        User user = userDao.findByUserId(userId);
        
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        
        if (user.matchPassword(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return jspView("redirect:/");
        } else {
            throw new IllegalStateException("비밀번호가 틀립니다.");
        }
    }
    
    @RequestMapping("/users/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return jspView("redirect:/");
    }
}
