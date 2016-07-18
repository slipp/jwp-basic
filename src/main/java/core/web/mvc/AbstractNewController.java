package core.web.mvc;

import core.web.view.JsonView;
import core.web.view.JspView;
import core.web.view.ModelAndView;

public abstract class AbstractNewController {
    protected ModelAndView jspView(String forwardUrl) {
        return new ModelAndView(new JspView(forwardUrl));
    }

    protected ModelAndView jsonView() {
        return new ModelAndView(new JsonView());
    }
}
