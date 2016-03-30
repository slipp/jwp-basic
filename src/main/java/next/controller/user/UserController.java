package next.controller.user;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import core.web.argumentresolver.LoginUser;
import next.model.User;
import next.repository.UserRepository;
import next.service.SessionComponent;

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
    public String index(@LoginUser User loginUser, Model model) throws Exception {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }
    
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public String profile(@PathVariable String userId, Model model) throws Exception {
    	model.addAttribute("user", userRepository.findByUserId(userId));
        return "/user/profile";
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String form(Model model) throws Exception {
    	model.addAttribute("user", new User());
    	return "/user/form";
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
	public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) throws Exception {
        log.debug("User : {}", user);
        if (bindingResult.hasErrors()) {
        	return "/user/form";
        }
        
        userRepository.save(user);
		return "redirect:/";
	}
    
    @RequestMapping(value = "/{userId}/edit", method = RequestMethod.GET)
	public String updateForm(@LoginUser User loginUser, @PathVariable String userId, Model model) throws Exception {
    	User user = userRepository.findByUserId(userId);
    	if (!loginUser.isSameUser(user)) {
        	throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
    	model.addAttribute("user", user);
    	return "/user/edit";
	}
    
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
	public String update(@LoginUser User loginUser, @PathVariable String userId, 
			@ModelAttribute("user") @Valid User newUser, BindingResult bindingResult) throws Exception {
    	log.debug("User : {}", newUser);
        if (bindingResult.hasErrors()) {
        	return "/user/edit";
        }
    	
		User user = userRepository.findByUserId(userId);
        if (!loginUser.isSameUser(newUser)) {
        	throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        
        log.debug("Update User : {}", newUser);
        user.update(newUser);
        userRepository.save(user);
        return "redirect:/";
	}
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm() throws Exception {
    	return "/user/login";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String userId, String password, HttpSession session, Model model) throws Exception {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            model.addAttribute("loginFailed", true);
            return "/user/login";
        }
        
        if (user.matchPassword(password)) {
            session.setAttribute(SessionComponent.USER_SESSION_KEY, user);
            return "redirect:/";
        } else {
        	model.addAttribute("loginFailed", true);
            return "/user/login";
        }
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) throws Exception {
        session.removeAttribute(SessionComponent.USER_SESSION_KEY);
        return "redirect:/";
    }
}
