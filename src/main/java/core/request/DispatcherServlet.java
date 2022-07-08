package core.request;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebServlet(name="dispatcherServlet", urlPatterns= "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
	
	@Override
	public void init() {
		log.debug("init 제대로 온거 맞음?");
		WebServlet s;
	}
}
