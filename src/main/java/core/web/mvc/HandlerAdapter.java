package core.web.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.web.view.ModelAndView;

public interface HandlerAdapter {
	boolean supports(Object handler);

	ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
