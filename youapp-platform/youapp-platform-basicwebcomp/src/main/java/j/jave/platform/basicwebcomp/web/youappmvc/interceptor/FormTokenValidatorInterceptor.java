package j.jave.platform.basicwebcomp.web.youappmvc.interceptor;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.io.JFile;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.platform.basicwebcomp.web.form.DefaultVoidDuplicateSubmitService;
import j.jave.platform.basicwebcomp.web.form.FormIdentification;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContextHolder;
import j.jave.platform.basicwebcomp.web.youappmvc.ViewConstants;
import j.jave.platform.basicwebcomp.web.youappmvc.container.HttpInvokeContainerDelegateService;
import j.jave.platform.basicwebcomp.web.youappmvc.jsonview.JSONServletViewHandler;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FormTokenValidatorInterceptor implements ServletRequestInterceptor {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(FormTokenValidatorInterceptor.class);
	
	private JServletViewHandler servletViewHandler=new JSONServletViewHandler();
	
	private DefaultVoidDuplicateSubmitService voidDuplicateSubmitService=new DefaultVoidDuplicateSubmitService();
	
	@Override
	public Object intercept(ServletRequestInvocation servletRequestInvocation) {
		
		HttpServletRequest req=servletRequestInvocation.getHttpServletRequest();
		HttpServletResponse resp=servletRequestInvocation.getHttpServletResponse();
		
		try{
			String formToken=req.getParameter(ViewConstants.FORM_TOKEN_PARAMETER);
			FormIdentification formIdentification= JJSON.get().parse(formToken, FormIdentification.class);
			boolean valid=voidDuplicateSubmitService.validate(formIdentification);
			if(valid){
				return servletRequestInvocation.proceed();
			}
			else{
				
			}
			
			
		}
		catch(Exception e){
			LOGGER.error(e.getMessage(), e); 
			return ServletExceptionUtil.exception(req, resp, e);
		}
		
	}
	
	
	
}
