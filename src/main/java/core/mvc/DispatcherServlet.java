package core.mvc;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.HandlerExecution;
import core.nmvc.HandlerMapping;

@WebServlet(name = "dispatcher", urlPatterns = {"", "/"}, loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

	private List<HandlerMapping> mappings = Lists.newArrayList();

	@Override
	public void init() throws ServletException {
		LegacyHandlerMapping lhm = new LegacyHandlerMapping();
		lhm.initMapping();
		AnnotationHandlerMapping ahm = new AnnotationHandlerMapping("next.controller");
		ahm.initialize();
		
		mappings.add(lhm);
		mappings.add(ahm);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestUri = req.getRequestURI();
		logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

		Object handler = getHandler(req);
		if (handler == null) {
			throw new IllegalArgumentException("존재하지 않는 URL입니다.");
		}
		
		try {
			ModelAndView mav = execute(handler, req, resp);
			View view = mav.getView();
			view.render(mav.getModel(), req, resp);
		} catch (Throwable e) {
			logger.error("Exception : {}", e);
			throw new ServletException(e.getMessage());
		}
	}

	private Object getHandler(HttpServletRequest req) {
		for (HandlerMapping handlerMapping : mappings) {
			Object handler = handlerMapping.getHandler(req);
			if (handler != null) {
				return handler;
			}
		}
		return null;
	}
	
	private ModelAndView execute(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (handler instanceof Controller) {
			return ((Controller)handler).execute(req, resp);
		} else {
			return ((HandlerExecution)handler).handle(req, resp);
		}
	}
}
