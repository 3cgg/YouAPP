package j.jave.web.htmlclient.form;

import java.util.concurrent.locks.ReentrantLock;

import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.jave.support.validate.JValidatingException;
import me.bunny.kernel.jave.utils.JAssert;
import me.bunny.kernel.jave.utils.JStringUtils;
import me.bunny.kernel.jave.utils.JUniqueUtils;

public class DefaultVoidDuplicateSubmitService extends JServiceFactorySupport<VoidDuplicateSubmitService>
implements VoidDuplicateSubmitService {
	
	private TokenStorageService tokenStorageService=TokenStorageServiceUtil.get();
	
	private ReentrantLock lock=new ReentrantLock();
	
	@Override
	public boolean validate(String formId, String token) {
		try{
			lock.lockInterruptibly();
			FormIdentification formIdentification=tokenStorageService.getToken(formId);
			if(formIdentification==null){
				throw new JValidatingException("the form ["+formId+"] is invlaid, check if the form need validate.");
			}
			String storageToken=formIdentification.getToken();
			if(JStringUtils.isNotNullOrEmpty(storageToken)){
				boolean valid= storageToken.equals(token);
				tokenStorageService.removeByFormId(formId);
				return valid;
			}
			else{
				throw new JValidatingException("the form ["+formId+"] is invlaid, "+
						(token==null?"token is missing.":("token : "+token))
						);
			}
		}catch(InterruptedException e){
			return validate(formId, token);
		}catch(Exception e){
			if(JValidatingException.class.isInstance(e)){
				throw (JValidatingException)e;
			}
			throw new JValidatingException(e);
		}finally{
			if(lock.isHeldByCurrentThread()){
				lock.unlock();
			}
		}
	}
	
	@Override
	public boolean validate(FormIdentification formIdentification) {
		return validate(formIdentification.getFormId(),formIdentification.getToken());
	}
	
	@Override
	public boolean cleanup(String sessionId) {
		return tokenStorageService.removeBySessionId(sessionId);
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
	
	@Override
	protected VoidDuplicateSubmitService doGetService() {
		return this;
	}
	
	@Override
	public Class<?> getServiceImplClass() {
		return getClass();
	}
}
