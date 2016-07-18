package core.nmvc;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.web.mvc.ControllerScanner;

public class ControllerScannerTest {
    private static final Logger logger = LoggerFactory.getLogger(ControllerScannerTest.class);

    private ControllerScanner cf;

    @Before
    public void setup() {
        cf = new ControllerScanner("core.nmvc");
    }

    @Test
    public void getControllers() throws Exception {
        Map<Class<?>, Object> controllers = cf.getControllers();
        for (Class<?> controller : controllers.keySet()) {
            logger.debug("controller : {}", controller);
        }
    }
}
