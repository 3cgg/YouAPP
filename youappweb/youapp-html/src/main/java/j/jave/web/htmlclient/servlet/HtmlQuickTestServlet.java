package j.jave.web.htmlclient.servlet;

import j.jave.web.htmlclient.DefaultHtmlFileService;
import j.jave.web.htmlclient.thymeleaf.ServletTemplateResolver;
import j.jave.web.htmlclient.thymeleaf.ThymeleafHtmlFileService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlQuickTestServlet extends HttpServlet{

	private static final Logger LOGGER=LoggerFactory.getLogger(HtmlQuickTestServlet.class);
	
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
				bytes=getThymeleafHtmlFileService().getHtmlFile("/ui/index-quick-test.html", data);
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
    			writeBytesDirectly(req, resp, bytes);
    		}catch(Exception e){
    			writeBytesDirectly(req, resp,"the server (json decode) error.".getBytes());
    		}
		}

	}
	
	
	public static final void writeBytesDirectly(HttpServletRequest request,
			HttpServletResponse response,byte[] bytes){
		OutputStream outputStream=null;
		try {
			outputStream=response.getOutputStream();
			response.getOutputStream().write(bytes);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(outputStream!=null){
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
}
