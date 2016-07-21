package j.jave.web.htmlclient.thymeleaf;

import javax.servlet.ServletContext;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

public class ServletTemplateResolver {

	private static TemplateEngine templateEngine;
    
    public static void initializeTemplateEngine(ServletContext servletContext) {
        
        ServletContextTemplateResolver templateResolver = 
            new ServletContextTemplateResolver(servletContext);
        // XHTML is the default mode, but we set it anyway for better understanding of code
        templateResolver.setTemplateMode("XHTML");
        // This will convert "home" to "/WEB-INF/templates/home.html"
        templateResolver.setPrefix("/");
//        templateResolver.setSuffix(".html");
        // Template cache TTL=1h. If not set, entries would be cached until expelled by LRU
        templateResolver.setCacheTTLMs(3600000L);
        templateResolver.setCacheable(false);
        
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        
    }
    
    static TemplateEngine getTemplateEngine() {
		return templateEngine;
	}
    
    
	
}
