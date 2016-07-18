package core.nmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.ModelAndView;

@Controller
public class MyController {
    private static final Logger logger = LoggerFactory.getLogger(MyController.class);

    @RequestMapping("/users/findUserId")
    public ModelAndView findUserId(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("findUserId");
        return null;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("save");
        return null;
    }
}
