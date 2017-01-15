package j.jave.web.htmlclient.servlet;

import j.jave.web.htmlclient.DefaultHtmlFileService;
import j.jave.web.htmlclient.thymeleaf.ServletTemplateResolver;
import j.jave.web.htmlclient.thymeleaf.ThymeleafHtmlFileService;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/quicktest/gethtml/*"})
public class HtmlQuickTestServlet extends HttpServlet{

	private static final JLogger LOGGER=JLoggerFactory.getLogger(HtmlQuickTestServlet.class);
	
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
	
	private ThymeleafHtmlFileService thymeleafHtmlFileService=null;
	
	public ThymeleafHtmlFileService getThymeleafHtmlFileService() {
		if(thymeleafHtmlFileService==null){
			thymeleafHtmlFileService=new ThymeleafHtmlFileService();
		}
		return thymeleafHtmlFileService;
	}
	
	private DefaultHtmlFileService defaultHtmlFileService=new DefaultHtmlFileService();
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		byte[] bytes=null;
		try{
			String path=req.getPathInfo();
			byte[] in_bytes=defaultHtmlFileService.getHtmlFile(path, new HashMap<String, Object>());
			
			if(path.endsWith(".html")){
				Map<String,Object> data=new HashMap<String, Object>();
				data.put("ac_html", new String(in_bytes,"utf-8"));
				bytes=getThymeleafHtmlFileService().getHtmlFile("/ui/pages/index-quick-test.html", data);
			}
			else{
				bytes=in_bytes;
			}
	        
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			if(e.getMessage()==null){
				bytes="null ".getBytes("utf-8");
			}
			else{
				bytes=e.getMessage().getBytes("utf-8");
			}
		}finally{
			try{
				ServletUtil.writeBytesDirectly(req, resp, bytes);
    		}catch(Exception e){
    			ServletUtil.writeBytesDirectly(req, resp,"the server (json decode) error.".getBytes());
    		}
		}

	}
	
}
