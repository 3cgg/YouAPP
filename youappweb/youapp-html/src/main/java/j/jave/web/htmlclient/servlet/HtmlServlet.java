package j.jave.web.htmlclient.servlet;

import j.jave.web.htmlclient.SyncHtmlResponseService;
import j.jave.web.htmlclient.interceptor.DefaultHtmlRequestServletRequestInvocation;
import j.jave.web.htmlclient.interceptor.HtmlRequestServletRequestInvocation;
import j.jave.web.htmlclient.thymeleaf.ServletTemplateResolver;
import me.bunny.kernel.jave.json.JJSON;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/get/gethtml/*"})
public class HtmlServlet extends HttpServlet{

	private static final JLogger LOGGER=JLoggerFactory.getLogger(HtmlServlet.class);
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletTemplateResolver.initializeTemplateEngine(config.getServletContext());
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	private SyncHtmlResponseService syncHtmlResponseService=SyncHtmlResponseService.get();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Object syncHtmlResponse=null;
		try{
			HtmlRequestServletRequestInvocation invocation=new DefaultHtmlRequestServletRequestInvocation(req, resp);
			syncHtmlResponse=invocation.proceed();
	        
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			syncHtmlResponse=syncHtmlResponseService.miniError(e.getMessage());
		}finally{
			try{
    			String out=JJSON.get().formatObject(syncHtmlResponse);
    			ServletUtil.writeBytesDirectly(req, resp, out.getBytes("utf-8"));
    		}catch(Exception e){
    			ServletUtil.writeBytesDirectly(req, resp,"the server (json decode) error.".getBytes());
    		}
		}
	}
	
	
}
