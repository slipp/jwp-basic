package core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(filterName="Set Character Encoding",urlPatterns="/*",initParams=
{@WebInitParam(name="encoding",value="UTF-8")})

public class CharacterEncodingFilter implements Filter {
	private static final String DEFAULT_ENCODING = "UTF-8";
	 
    protected String encoding = null;  
    protected FilterConfig filterConfig = null;  
    protected boolean ignore = true;  
     
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {  
        this.filterConfig = filterConfig;  
        this.encoding = filterConfig.getInitParameter("encoding");  
        String value = filterConfig.getInitParameter("ignore");  
        if (value == null)  
          this.ignore = true;  
        else if (value.equalsIgnoreCase("true"))  
          this.ignore = true;  
        else if (value.equalsIgnoreCase("yes"))  
          this.ignore = true;  
        else  
          this.ignore = false;  
    }  

	@Override
    public void doFilter(ServletRequest request, ServletResponse response,  
            FilterChain chain) throws IOException, ServletException {  
       // Conditionally select and set the character encoding to be used  
       if (ignore || (request.getCharacterEncoding() == null)){  
         String encoding = selectEncoding(request);            
         if (encoding != null){
           request.setCharacterEncoding(encoding); 
         }
       }  
       chain.doFilter(request, response);  
     }  
	

	@Override
    public void destroy() {  
        this.encoding = null;  
        this.filterConfig = null;  
    }  

    protected String selectEncoding(ServletRequest request) {  
    	return (this.encoding);  
    }
}
