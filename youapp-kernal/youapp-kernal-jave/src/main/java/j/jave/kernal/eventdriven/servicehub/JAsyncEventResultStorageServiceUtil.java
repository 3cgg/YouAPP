package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.JProperties;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.reflect.JClassUtils;

public class JAsyncEventResultStorageServiceUtil implements JAsyncEventResultStorageService {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(JAsyncEventResultStorageServiceUtil.class);
	
	private JAsyncEventResultStorageService asyncEventResultRepoService;
	
	private final Object sync=new Object();
	
	private JAsyncEventResultStorageService getRepoService() {
		
		if(asyncEventResultRepoService==null){
			synchronized (sync) {
				if(asyncEventResultRepoService==null){
					String repoService=JConfiguration.get().getString(JProperties.ASYNC_EVENT_RESULT_REPO_SERVICE, JDefaultAsyncEventResultStorageService.class.getName());
					if(JDefaultAsyncEventResultStorageService.class.getName().equals(repoService)){
						asyncEventResultRepoService=JServiceHubDelegate.get().getActualService(this, JDefaultAsyncEventResultStorageService.class);;
					}
					else{
						try{
							Class<JAsyncEventResultStorageService> clazz=JClassUtils.load(repoService);
							asyncEventResultRepoService=JServiceHubDelegate.get().getActualService(this, clazz);
						}catch(Exception e){
							LOGGER.info(e.getMessage(), e);
							asyncEventResultRepoService=JServiceHubDelegate.get().getActualService(this, JDefaultAsyncEventResultStorageService.class);;
							LOGGER.warn(repoService +" cannot found in the service hub, instead use default repo service : "+JDefaultAsyncEventResultStorageService.class.getName());
						}
					}
				
				
				}
			}
		}
		return asyncEventResultRepoService;
	}
	
	private JAsyncEventResultStorageServiceUtil() {}
	
	private static final JAsyncEventResultStorageServiceUtil instance=new JAsyncEventResultStorageServiceUtil();
	
	public static final JAsyncEventResultStorageService get(){
		return instance;
	}
	
	@Override
	public EventExecutionResult getEventResult(String eventUnique)
			throws JEventExecutionException {
		return getRepoService().getEventResult(eventUnique);
	}

	@Override
	public void putEventResult(JEventExecution eventExecution) {
		getRepoService().putEventResult(eventExecution);
	}
	
	
}
