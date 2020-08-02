package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface View {
	void render(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
