package core.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class JsonView implements View {

	@Override
	public void render(final Map<String, ?> model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(objectMapper.writeValueAsString(model));
	}

	private Map<String, Object> createModelFromRequest(final HttpServletRequest request) {
		Map<String, Object> models = new HashMap<>();
		Enumeration<String> attributeNames = request.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			String name = attributeNames.nextElement();
			models.put(name, request.getAttribute(name));
		}
		return models;
	}
}
