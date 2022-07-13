package core.request;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.requestMapping.RequestMapping;


@WebServlet(name="dispatcherServlet", urlPatterns= "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			log.debug("요청 온 method : {}", req.getMethod());
			log.debug("요청 온 request URI : {}", req.getRequestURI());
			RequestDispatcher rd = req.getRequestDispatcher(RequestMapping.getController(req.getRequestURI(), req, resp));
			rd.forward(req, resp);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) {
		try {
			log.debug("요청 온 method : {}", req.getMethod());
			log.debug("요청 온 request URI : {}", req.getRequestURI());
			RequestDispatcher rd = req.getRequestDispatcher(RequestMapping.getController(req.getRequestURI(), req, resp));
			rd.forward(req, resp);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
