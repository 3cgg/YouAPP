package j.jave.web.htmlclient.servlet;

import j.jave.web.htmlclient.interceptor.DefaultFileUploaderRequestServletRequestInvocation;
import j.jave.web.htmlclient.interceptor.FileUploaderRequestServletRequestInvocation;
import j.jave.web.htmlclient.response.ResponseModel;
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

@WebServlet(urlPatterns={"/get/fileupload/*"})
public class UploaderServlet extends HttpServlet{

	private static final JLogger LOGGER=JLoggerFactory.getLogger(UploaderServlet.class);
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
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
			FileUploaderRequestServletRequestInvocation invocation=new DefaultFileUploaderRequestServletRequestInvocation(req, resp);
        	respModel=invocation.proceed();
        	
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			respModel=ResponseModel.newError().setData(e.getMessage());
		}finally{
			try{
    			String out=JJSON.get().formatObject(respModel);
    			ServletUtil.writeBytesDirectly(req, resp, out.getBytes("utf-8"));
    		}catch(Exception e){
    			ServletUtil.writeBytesDirectly(req, resp,"the server (json decode) error.".getBytes());
    		}
		}
	}
	
	
	
	
	
	
}
