package core.web.mvc;

import next.config.MyConfiguration;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import core.di.factory.ApplicationContext;

public class AnnotationHandlerMappingTest {
	private AnnotationHandlerMapping handlerMapping;
	
	@Before
	public void setup() {
		ApplicationContext ac = new ApplicationContext(MyConfiguration.class);
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
