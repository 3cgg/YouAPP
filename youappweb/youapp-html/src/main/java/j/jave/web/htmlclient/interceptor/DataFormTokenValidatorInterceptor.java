package j.jave.web.htmlclient.interceptor;

import j.jave.web.htmlclient.form.FormIdentification;
import j.jave.web.htmlclient.form.VoidDuplicateSubmitService;
import j.jave.web.htmlclient.response.ResponseModel;
import me.bunny.kernel._c.json.JJSON;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

/**
 * promise the form only can be submit once.
 * @author J
 *
 */
public class DataFormTokenValidatorInterceptor implements  DataRequestServletRequestInterceptor{

	private static final JLogger LOGGER=JLoggerFactory.getLogger(DataFormTokenValidatorInterceptor.class);
	
	private VoidDuplicateSubmitService voidDuplicateSubmitService=
			JServiceHubDelegate.get().getService(this, VoidDuplicateSubmitService.class);
	
	@Override
	public Object intercept(DataRequestServletRequestInvocation servletRequestInvocation) {
		
		try{
			
			String formToken=servletRequestInvocation.getRequestVO().getToken();
			if(JStringUtils.isNullOrEmpty(formToken)){
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
							if(!responseModel.isSuccess()){
								//refresh token.
								 responseModel.setToken(voidDuplicateSubmitService.newFormIdentification());
							}
						}
						return result;
					}
					else{
						ResponseModel responseModel= ResponseModel.newError();
						responseModel.setData("the token is invalid.");
						responseModel.setToken(voidDuplicateSubmitService.newFormIdentification());
						return responseModel;
					}
				}catch(Exception e){
					//refresh token.
					return ResponseModel.newError().setData(e.getMessage()).setToken(voidDuplicateSubmitService.newFormIdentification());
				}
			}
		}catch(Throwable e){
			LOGGER.error(e.getMessage(), e); 
			throw e;
		}
		
	}
	
	
	
}
