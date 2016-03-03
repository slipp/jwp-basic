package core.mvc;


public abstract class AbstractController implements Controller {
	protected ModelAndView jspView(String forwardUrl) {
		return new ModelAndView(new JstlView(forwardUrl));
	}
	
	protected ModelAndView jsonView() {
		return new ModelAndView(new JsonView());
	}	
}
