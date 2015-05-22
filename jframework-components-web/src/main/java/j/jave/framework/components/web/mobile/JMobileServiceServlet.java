/**
 * 
 */
package j.jave.framework.components.web.mobile;

import j.jave.framework.components.web.model.JHttpContext;
import j.jave.framework.components.web.multi.platform.servlet.JServiceServlet;
import j.jave.framework.json.JJSON;
import j.jave.framework.servicehub.exception.JServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 *
 */
public class JMobileServiceServlet  extends JServiceServlet {

	private static final Logger LOGGER=LoggerFactory.getLogger(JMobileServiceServlet.class);
	
	@Override
	protected void handlerNavigate(HttpServletRequest request,
			HttpServletResponse response,JHttpContext httpContext, Object navigate) throws Exception {
		MobileResult mobileResult=(MobileResult)navigate;
		mobileResult.setStatus("1");
		String out=JJSON.get().format(mobileResult);
		response.getOutputStream().write(out.getBytes("utf-8"));
	}
	
	@Override
	protected void handlerServiceExcepion(HttpServletRequest request,
			HttpServletResponse response, JHttpContext httpContext,
			JServiceException exception) {
		try{
			MobileResult mobileResult=new MobileResult();
			mobileResult.setStatus("0");
			mobileResult.setData(exception.getMessage());
			String out=JJSON.get().format(mobileResult);
			response.getOutputStream().write(out.getBytes("utf-8"));
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	@Override
	protected void handlerExcepion(HttpServletRequest request,
			HttpServletResponse response, JHttpContext httpContext,
			Exception exception) {
		try{
			Throwable exp=exception;
			while(exp.getCause()!=null){
				exp=exp.getCause();
			}
			
			if(JServiceException.class.isInstance(exp)){
				handlerServiceExcepion(request, response, httpContext, (JServiceException) exp);
			}
			else{
				MobileResult mobileResult=new MobileResult();
				mobileResult.setStatus("-1");
				mobileResult.setData(exception.getMessage());
				String out=JJSON.get().format(mobileResult);
				response.getOutputStream().write(out.getBytes("utf-8"));
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
	}
	
}
