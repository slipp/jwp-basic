package next.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.web.WebApplicationInitializer;

public class MyWebApplicationInitializer implements WebApplicationInitializer {
	private static final Logger log = LoggerFactory.getLogger(MyWebApplicationInitializer.class);
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		log.info("Start My WebApplication Initializer");
	}
}
