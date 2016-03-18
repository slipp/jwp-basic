package next.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.di.factory.ApplicationContext;
import core.web.WebApplicationInitializer;
import core.web.mvc.AnnotationHandlerMapping;
import core.web.mvc.DispatcherServlet;

public class MyWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(MyWebApplicationInitializer.class);
    
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
    	ApplicationContext ac = new ApplicationContext(MyConfiguration.class);
    	AnnotationHandlerMapping ahm = new AnnotationHandlerMapping(ac);
    	ahm.initialize();
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(ahm));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("", "/");
        
        log.info("Start MyWebApplication Initializer");
    }
}
