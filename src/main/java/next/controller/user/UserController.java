package next.controller.user;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import next.controller.UserSessionUtils;
import next.model.User;
import next.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import core.web.argumentresolver.LoginUser;

@Controller
@RequestMapping("/users")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserRepository userRepository;

    @Inject
    public UserController(UserRepository userRepository) {
    	this.userRepository = userRepository;
    }

	@RequestMapping(value = "", method = RequestMethod.GET)
    public String list(@LoginUser(UserSessionUtils.USER_SESSION_KEY) User user, Model model) throws Exception {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }
    
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public String profile(@PathVariable String userId, Model model) throws Exception {
    	model.addAttribute("user", userRepository.findByUserId(userId));
        return "/user/profile";
    }
    
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String form(Model model) throws Exception {
    	model.addAttribute("user", new User());
    	return "/user/form";
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
	public String create(User user) throws Exception {
        log.debug("User : {}", user);
        userRepository.save(user);
		return "redirect:/";
	}
    
    @RequestMapping(value = "/{userId}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable String userId, HttpSession session, Model model) throws Exception {
    	User user = userRepository.findByUserId(userId);
    	
    	if (!UserSessionUtils.isSameUser(session, user)) {
        	throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
    	model.addAttribute("user", user);
    	return "/user/form";
	}
    
    @RequestMapping(value = "", method = RequestMethod.PUT)
	public String update(User newUser, HttpSession session) throws Exception {
		User user = userRepository.findByUserId(newUser.getUserId());
        if (!UserSessionUtils.isSameUser(session, user)) {
        	throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        
        log.debug("Update User : {}", newUser);
        user.update(newUser);
        userRepository.save(user);
        return "redirect:/";
	}
    
    @RequestMapping(value = "/loginForm", method = RequestMethod.GET)
    public String loginForm() throws Exception {
    	return "/user/login";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String userId, String password, HttpSession session) throws Exception {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        
        if (user.matchPassword(password)) {
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return "redirect:/";
        } else {
            throw new IllegalStateException("비밀번호가 틀립니다.");
        }
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) throws Exception {
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }
}
