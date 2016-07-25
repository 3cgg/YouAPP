package j.jave.web.htmlclient.servlet;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.web.htmlclient.interceptor.DataRequestServletRequestInvocation;
import j.jave.web.htmlclient.interceptor.DefaultDataRequestServletRequestInvocation;
import j.jave.web.htmlclient.response.ResponseModel;
import j.jave.web.htmlclient.thymeleaf.ServletTemplateResolver;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/get/getdata/*"})
public class DataServlet extends HttpServlet{

	private static final JLogger LOGGER=JLoggerFactory.getLogger(DataServlet.class);
	
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
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Object respModel=null;
		try{
			DataRequestServletRequestInvocation invocation=new DefaultDataRequestServletRequestInvocation(req, resp);
        	respModel=invocation.proceed();
        	
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			respModel=ResponseModel.newError().setData(e.getMessage());
		}finally{
			try{
    			String out=JJSON.get().formatObject(respModel);
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
