package core.nmvc;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class AnnotationHandlerMappingTest {
	private AnnotationHandlerMapping ahm;
	private MockHttpServletResponse response;
	
	@Before
	public void setup() {
		ahm = new AnnotationHandlerMapping("core.controller");
		response = new MockHttpServletResponse();
	}
	
	@Test
	public void list() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users");
		HandlerExecution he = ahm.getHandler(request);
		he.handle(request, response);
	}
	
	@Test
	public void updateForm() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "/users/updateForm");
		HandlerExecution he = ahm.getHandler(request);
		he.handle(request, response);
	}
	
	@Test
	public void update() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest("POST", "/users/update");
		HandlerExecution he = ahm.getHandler(request);
		he.handle(request, response);
	}
}
