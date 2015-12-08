package core.web;

import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import freemarker.cache.WebappTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public abstract class AbstractBaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void process(String templatePath, HttpServletResponse resp) {
		process(templatePath, new Object(), resp);
	}
	
	protected void process(String templatePath, Object data, HttpServletResponse resp) {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateLoader(new WebappTemplateLoader(getServletContext()));
		Template template;
		try {
			template = cfg.getTemplate(templatePath);
			Writer writer = new OutputStreamWriter(resp.getOutputStream());
			template.process(data, writer);
			writer.flush();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
