package j.jave.platform.basicwebcomp.web.form;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.platform.basicwebcomp.WebCompProperties;

public class TokenStorageServiceUtil implements TokenStorageService {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(TokenStorageServiceUtil.class);
	
	private TokenStorageService tokenStorageService;
	
	private JServiceHubDelegate serviceHubDelegate=JServiceHubDelegate.get();
	
	private final Object sync=new Object();
	
	private TokenStorageService getTokenStorage(){
		if(tokenStorageService==null){
			synchronized (sync) {
				if(tokenStorageService==null){
					String tokenStorageServiceName=JConfiguration.get().getString(WebCompProperties.YOUAPPMVC_FORM_TOKEN_STORAGE,
							DefaultMemoryTokenStorage.class.getName());
					if(DefaultMemoryTokenStorage.class.getName().equals(tokenStorageServiceName)){
						tokenStorageService=serviceHubDelegate.getService(this, DefaultMemoryTokenStorage.class);
					}
					else{
						try{
							Class<TokenStorageService> clazz=JClassUtils.load(tokenStorageServiceName);
							tokenStorageService=serviceHubDelegate.getService(this, clazz);
						}catch(Exception e){
							LOGGER.info(e.getMessage(), e);
							tokenStorageService=serviceHubDelegate.getService(this, DefaultMemoryTokenStorage.class);
							LOGGER.warn(tokenStorageServiceName +" cannot found in the service hub, instead use default token storage service : "+DefaultMemoryTokenStorage.class.getName());
						}
					}
				}
			}
		}
		return tokenStorageService;
	}
	
	private static final TokenStorageServiceUtil INSTANCE=new TokenStorageServiceUtil();
	
	public static TokenStorageService get(){
		return INSTANCE;
	}

	@Override
	public String getSessionId() {
		return getTokenStorage().getSessionId();
	}

	@Override
	public boolean store(FormIdentification formIdentification) {
		return getTokenStorage().store(formIdentification);
	}

	@Override
	public FormIdentification getToken(String formId) {
		return getTokenStorage().getToken(formId);
	}

	@Override
	public boolean removeBySessionId(String sessionId) {
		return getTokenStorage().removeBySessionId(sessionId);
	}
	
	@Override
	public boolean removeByFormId(String formId) {
		return getTokenStorage().removeByFormId(formId);
	}

}
