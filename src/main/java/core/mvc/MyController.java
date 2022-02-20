package core.mvc;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MyController {
    private static final Logger log = LoggerFactory.getLogger(MyController.class);

    @RequestMapping("/users/findUserId")
    public ModelAndView list(HttpServletRequest req, HttpServletResponse res) {
        log.debug("users findUserId");
        return new ModelAndView(new JspView("/users/list.jsp"));
    }

    @RequestMapping(value = "/users/show", method = RequestMethod.GET)
    public ModelAndView show(HttpServletRequest req, HttpServletResponse res) {
        log.debug("users findUserId");
        return new ModelAndView(new JspView("/users/show.jsp"));
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest req, HttpServletResponse res) {
        log.debug("users create");
        return new ModelAndView(new JspView("redirect:/users"));
    }
}
