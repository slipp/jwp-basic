package next.web;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import core.db.DataBase;
import freemarker.cache.WebappTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import next.model.User;

@WebServlet("/create")
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateUserServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User(
                req.getParameter("userId"),
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email"));
        LOGGER.debug("User : {}", user);
        DataBase.addUser(user);
        Map<String, Collection<User>> data = Maps.newHashMap();
        data.put("users", DataBase.findAll());
        
        Template template = getTemplate();
        Writer writer = new OutputStreamWriter(resp.getOutputStream());
        try {
			template.process(data, writer);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage());
		}
        writer.flush();
    }
    
    private Template getTemplate() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateLoader(new WebappTemplateLoader(getServletContext()));
        try {
            return cfg.getTemplate("index.ftl");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
