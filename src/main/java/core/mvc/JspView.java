package core.mvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class JspView implements View {

	private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

	private final String viewName;

	public JspView(final String viewName) {
		Objects.requireNonNull(viewName, "viewName is null.");
		this.viewName = viewName;
	}

	@Override
	public void render(final Map<String, ?> model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
			response.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
			return;
		}

		Set<String> keys = model.keySet();
		for (String key : keys) {
			request.setAttribute(key, model.get(key));
		}

		RequestDispatcher rd = request.getRequestDispatcher(viewName);
		rd.forward(request, response);
	}
}
