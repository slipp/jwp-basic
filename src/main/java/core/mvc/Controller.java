package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
    ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
