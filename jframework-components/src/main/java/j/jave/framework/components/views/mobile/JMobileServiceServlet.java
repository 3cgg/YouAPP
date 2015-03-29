/**
 * 
 */
package j.jave.framework.components.views.mobile;

import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.views.HTTPContext;
import j.jave.framework.components.views.JServiceServlet;
import j.jave.framework.json.JJSON;

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
			HttpServletResponse response,HTTPContext httpContext, Object navigate) throws Exception {
		MobileResult mobileResult=(MobileResult)navigate;
		mobileResult.setStatus("1");
		String out=JJSON.get().format(mobileResult);
		response.getOutputStream().write(out.getBytes("utf-8"));
	}
	
	@Override
	protected void handlerServiceExcepion(HttpServletRequest request,
			HttpServletResponse response, HTTPContext httpContext,
			ServiceException exception) {
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
			HttpServletResponse response, HTTPContext httpContext,
			Exception exception) {
		try{
			Throwable exp=exception;
			while(exp.getCause()!=null){
				exp=exp.getCause();
			}
			
			if(ServiceException.class.isInstance(exp)){
				handlerServiceExcepion(request, response, httpContext, (ServiceException) exp);
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
