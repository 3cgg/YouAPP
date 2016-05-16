package j.jave.platform.basicwebcomp.web.form;

import j.jave.kernal.jave.support.validate.JValidatingException;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.utils.JUniqueUtils;

public class DefaultVoidDuplicateSubmitService implements VoidDuplicateSubmitService {
	
	private TokenStorageService tokenStorageService=TokenStorageServiceUtil.get();
	
	@Override
	public boolean validate(String formId, String token) {
		FormIdentification formIdentification=tokenStorageService.getToken(formId);
		if(formIdentification==null){
			throw new JValidatingException("the form ["+formId+"] is invlaid, check if the form need validate.");
		}
		String storageToken=formIdentification.getToken();
		if(JStringUtils.isNotNullOrEmpty(storageToken)){
			return storageToken.equals(token);
		}
		else{
			throw new JValidatingException("the form ["+formId+"] is invlaid, "+
					(token==null?"token is missing.":("token : "+token))
					);
		}
	}
	
	@Override
	public boolean validate(FormIdentification formIdentification) {
		return validate(formIdentification.getFormId(),formIdentification.getToken());
	}
	
	@Override
	public boolean cleanup(String sessionId) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	public FormIdentification newFormIdentification() {
		FormIdentification formIdentification=new FormIdentification();
		String sessionId=tokenStorageService.getSessionId();
		JAssert.isNotNull(sessionId);
		JAssert.isNotEmpty(sessionId);
		formIdentification.setSessionId(sessionId);
		String appendUnique=JUniqueUtils.unique();
		String formIId=sessionId+"_"+appendUnique;
		formIdentification.setFormId(formIId);
		formIdentification.setToken(JUniqueUtils.unique());
		
		//store ...
		tokenStorageService.store(formIdentification);
		
		return formIdentification;
	}
	
}
