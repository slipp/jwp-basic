package core.web.mvc;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import core.web.mvc.AnnotationHandlerMapping;
import core.web.mvc.HandlerExecution;

public class AnnotationHandlerMappingTest {
	private AnnotationHandlerMapping handlerMapping;
	
	@Before
	public void setup() {
		handlerMapping = new AnnotationHandlerMapping("core.nmvc");
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
