package next.support;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.annotation.Component;
import core.annotation.Inject;
import core.annotation.PostConstruct;

@Component
public class DBInitializer {
    private static final Logger log = LoggerFactory.getLogger(DBInitializer.class);

    @Inject
    private DataSource dataSource;

    @PostConstruct
    public void initialize() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, dataSource);

        log.info("Completed Load ServletContext!");
    }
}
