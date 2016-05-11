package core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.ModelAndView;

@Controller
public class TestUserController {
	private static final Logger log = LoggerFactory.getLogger(TestUserController.class);
	
    @RequestMapping("/users")
    public ModelAndView list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	log.debug("user list");
        return null;
    }

    @RequestMapping(value="/users/updateForm", method=RequestMethod.GET)
    public ModelAndView updateForm(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	log.debug("user updateForm");
        return null;
    }
    
    @RequestMapping(value="/users/update", method=RequestMethod.POST)
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	log.debug("user update");
        return null;
    }
}
