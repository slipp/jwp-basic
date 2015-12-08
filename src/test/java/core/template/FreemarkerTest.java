package core.template;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerTest {
    @Test
    public void run() throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/"));
        Template template = cfg.getTemplate("templates/helloworld.ftl");

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("message", "Hello World!");
        
        List<String> countries = new ArrayList<String>();
        countries.add("Korea");
        countries.add("United States");
        countries.add("Germany");
        countries.add("France");
         
        data.put("countries", countries);

        Writer out = new OutputStreamWriter(System.out);
        template.process(data, out);
        out.flush();
    }
}
