package core.di.factory;

import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanScannerTest {
	private Logger log = LoggerFactory.getLogger(BeanScannerTest.class);
	
	@Test
	public void scan() throws Exception {
		BeanScanner scanner = new BeanScanner("core.di.factory.example", "next.controller");
		Set<Class<?>> beans = scanner.scan();
		for (Class<?> clazz : beans) {
			log.debug("Bean : {}", clazz);
		}
	}
}
