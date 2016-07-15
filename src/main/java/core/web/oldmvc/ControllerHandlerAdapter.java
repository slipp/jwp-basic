package core.web.oldmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.web.mvc.HandlerAdapter;
import core.web.view.ModelAndView;

public class ControllerHandlerAdapter implements HandlerAdapter {
	@Override
	public boolean supports(Object handler) {
		return handler instanceof Controller;
	}

	@Override
	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return ((Controller) handler).execute(request, response);
	}
}
