package next.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.web.WebApplicationInitializer;
import core.web.mvc.DispatcherServlet;

public class MyWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(MyWebApplicationInitializer.class);
    
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // AnnotationApplicationContext를 생성한 후 DisptacherSerlvet의 생성자로 전달한다.
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet());
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("", "/");
        
        log.info("Start MyWebApplication Initializer");
    }
}
