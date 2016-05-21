package j.jave.platform.basicwebcomp.web.youappmvc.interceptor;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.platform.basicwebcomp.web.form.DefaultVoidDuplicateSubmitService;
import j.jave.platform.basicwebcomp.web.form.FormIdentification;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.model.ResponseStatus;
import j.jave.platform.basicwebcomp.web.youappmvc.ViewConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * promise the form only can be submit once.
 * @author J
 *
 */
public class FormTokenValidatorInterceptor implements ServletRequestInterceptor {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(FormTokenValidatorInterceptor.class);
	
	private DefaultVoidDuplicateSubmitService voidDuplicateSubmitService=new DefaultVoidDuplicateSubmitService();
	
	@Override
	public Object intercept(ServletRequestInvocation servletRequestInvocation) {
		
		HttpServletRequest req=servletRequestInvocation.getHttpServletRequest();
		HttpServletResponse resp=servletRequestInvocation.getHttpServletResponse();
		
		try{
			String formToken=req.getParameter(ViewConstants.FORM_TOKEN_PARAMETER);
			if(formToken==null){
				//no check
				return servletRequestInvocation.proceed(); 
			}
			else{
				try{
					FormIdentification formIdentification= JJSON.get().parse(formToken, FormIdentification.class);
					boolean valid=voidDuplicateSubmitService.validate(formIdentification);
					if(valid){
						Object result= servletRequestInvocation.proceed();
						if(ResponseModel.class.isInstance(result)){
							ResponseModel responseModel=(ResponseModel)result;
							if(responseModel.getStatus()!=ResponseStatus.SUCCESS){
								//refresh token.
								 responseModel.setToken(voidDuplicateSubmitService.newFormIdentification());
							}
						}
						return result;
					}
					else{
						ResponseModel responseModel= ResponseModel.newFormTokenInvalid();
						responseModel.setData("the token is invalid.");
						responseModel.setToken(voidDuplicateSubmitService.newFormIdentification());
						return responseModel;
					}
				}catch(Exception e){
					//refresh token.
					return ResponseModel.newError().setData(e.getMessage()).setToken(voidDuplicateSubmitService.newFormIdentification());
				}
			}
		}
		catch(Exception e){
			LOGGER.error(e.getMessage(), e); 
			return ServletExceptionUtil.exception(req, resp, e);
		}
		
	}
	
	
	
}
