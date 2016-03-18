package core.web.mvc;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import core.web.view.ModelAndView;
import core.web.view.View;

public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

	private List<HandlerMapping> mappings = Lists.newArrayList();
	private List<HandlerAdapter> handlerAdapters = Lists.newArrayList();
	
	private HandlerMapping hm;

	public DispatcherServlet(HandlerMapping hm) {
		this.hm = hm;
	}
	
	@Override
	public void init() throws ServletException {
		mappings.add(hm);
		handlerAdapters.add(new HandlerExecutionHandlerAdapter());
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
			if (mav != null) {
				View view = mav.getView();
				view.render(mav.getModel(), req, resp);
			}
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
		for (HandlerAdapter handlerAdapter : handlerAdapters) {
			if (handlerAdapter.supports(handler)) {
				return handlerAdapter.handle(req, resp, handler);
			}
		}
		return null;
	}
}
