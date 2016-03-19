package next.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

@Configuration
@Import(value = { 
        PersistenceJPAConfig.class
} )
public class ApplicationConfig {
    private static final Logger log = LoggerFactory.getLogger(ApplicationConfig.class);

    @Autowired
    private Environment         env;

    /**
     * Application custom initialization code.
     * <p/>
     * Spring profiles can be configured with a system property
     * -Dspring.profiles.active=javaee
     * <p/>
     */
    @PostConstruct
    public void initApp() {
    	log.debug("Looking for Spring profiles...");
        if (env.getActiveProfiles().length == 0) {
        	log.info("No Spring profile configured, running with default configuration.");
        } else {
            for (String profile : env.getActiveProfiles()) {
            	log.info("Detected Spring profile: {}", profile);
            }
        }
    }
}
