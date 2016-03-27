package core.web.mvc;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import core.di.context.support.AnnotationConfigApplicationContext;
import next.config.MyConfiguration;

public class AnnotationHandlerMappingTest {
	private AnnotationHandlerMapping handlerMapping;
	
	@Before
	public void setup() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
		handlerMapping = new AnnotationHandlerMapping(ac);
		handlerMapping.initialize();
	}

	@Test
	public void getHandler() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users/findUserId");
		MockHttpServletResponse response = new MockHttpServletResponse();
		HandlerExecution execution = handlerMapping.getHandler(request);
		execution.handle(request, response);
	}
}
