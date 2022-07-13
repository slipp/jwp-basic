package core.request;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
	private static final String DEFAULT_REDIRECT_PREFIX="redirect:";
	
	private RequestMapping requestMapping;
	
	@Override
	public void init() throws ServletException{
		requestMapping = new RequestMapping();
		requestMapping.init();
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		log.debug("요청 온 method : {}", req.getMethod());
		log.debug("요청 온 request URI : {}", req.getRequestURI());
		try {
			String forwardUrl = requestMapping.getController(req.getRequestURI(), req, resp);
			goMove(forwardUrl, req, resp);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ServletException(e.getMessage());
		}
	}
	

	public void goMove(String forwardUrl, HttpServletRequest req, HttpServletResponse resp) {
		try {
			if(forwardUrl.startsWith(DEFAULT_REDIRECT_PREFIX)){
				resp.sendRedirect(forwardUrl.substring(DEFAULT_REDIRECT_PREFIX.length()));
				return;
			}
			RequestDispatcher rd = req.getRequestDispatcher(forwardUrl);
			rd.forward(req, resp);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
