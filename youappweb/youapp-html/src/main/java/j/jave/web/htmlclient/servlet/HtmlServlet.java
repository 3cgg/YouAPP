package j.jave.web.htmlclient.servlet;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.web.htmlclient.HtmlService;
import j.jave.web.htmlclient.RequestParamNames;
import j.jave.web.htmlclient.SyncHtmlModel;
import j.jave.web.htmlclient.request.RequestHtml;
import j.jave.web.htmlclient.request.RequestUrl;
import j.jave.web.htmlclient.response.SyncHtmlResponse;
import j.jave.web.htmlclient.response.SyncHtmlResponseService;
import j.jave.web.htmlclient.thymeleaf.ServletTemplateResolver;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	private HtmlService htmlService=HtmlService.get();
	
	private SyncHtmlResponseService syncHtmlResponseService=SyncHtmlResponseService.get();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		SyncHtmlResponse syncHtmlResponse=null;
		try{
				String requestData=req.getParameter(RequestParamNames.REQUEST_DATA);
	        
	        if(LOGGER.isDebugEnabled()){
	        	LOGGER.debug("the request data-> "+requestData);
	        }
	        
	        if(requestData!=null&&requestData.length()>0){
	        	
	        	RequestUrl requestUrl=JJSON.get().parse(requestData, RequestUrl.class);
	        	
	        	RequestHtml requestHtml= requestUrl.getRequest();
	        	SyncHtmlModel syncHtmlModel= htmlService.getSyncHtmlModel(requestHtml);
	        	
	        	syncHtmlResponse= syncHtmlResponseService.getSyncHtmlResponse(requestUrl, syncHtmlModel);
	        }
	        else{
	        	throw new RuntimeException("request data is missing.");
	        }
	        
		}catch(Exception e){
			syncHtmlResponse=syncHtmlResponseService.miniError(e.getMessage());
		}finally{
			try{
    			String out=JJSON.get().formatObject(syncHtmlResponse);
    			writeBytesDirectly(req, resp, out.getBytes("utf-8"));
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
