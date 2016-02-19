/**
 * 
 */
package j.jave.platform.basicwebcomp.web.youappmvc.mobile;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.json.JJSON;
import j.jave.platform.basicwebcomp.web.youappmvc.model.JHttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.servlet.JServiceServlet.JServletViewHandle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author J
 */
public class JJSONServletViewHandler  implements JServletViewHandle {

	private static final Logger LOGGER=LoggerFactory.getLogger(JJSONServletViewHandler.class);
	
	@Override
	public void handleNavigate(HttpServletRequest request,
			HttpServletResponse response,JHttpContext httpContext, Object navigate) throws Exception {
		MobileResult mobileResult=(MobileResult)navigate;
		mobileResult.setStatus("1");
		String out=JJSON.get().formatObject(mobileResult);
		response.getOutputStream().write(out.getBytes("utf-8"));
	}
	
	@Override
	public void handleServiceExcepion(HttpServletRequest request,
			HttpServletResponse response, JHttpContext httpContext,
			JServiceException exception) {
		try{
			MobileResult mobileResult=new MobileResult();
			mobileResult.setStatus("0");
			mobileResult.setData(exception.getMessage());
			String out=JJSON.get().formatObject(mobileResult);
			response.getOutputStream().write(out.getBytes("utf-8"));
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void handleExcepion(HttpServletRequest request,
			HttpServletResponse response, JHttpContext httpContext,
			Exception exception) {
		try{
			Throwable exp=exception;
			while(exp.getCause()!=null){
				exp=exp.getCause();
			}
			
			if(JServiceException.class.isInstance(exp)){
				handleServiceExcepion(request, response, httpContext, (JServiceException) exp);
			}
			else{
				MobileResult mobileResult=new MobileResult();
				mobileResult.setStatus("-1");
				mobileResult.setData(exception.getMessage());
				String out=JJSON.get().formatObject(mobileResult);
				response.getOutputStream().write(out.getBytes("utf-8"));
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
	}
	
}
