package core.web.oldmvc;

import core.web.view.JsonView;
import core.web.view.JspView;
import core.web.view.ModelAndView;

public abstract class AbstractController implements Controller {
    protected ModelAndView jspView(String forwardUrl) {
        return new ModelAndView(new JspView(forwardUrl));
    }

    protected ModelAndView jsonView() {
        return new ModelAndView(new JsonView());
    }
}
