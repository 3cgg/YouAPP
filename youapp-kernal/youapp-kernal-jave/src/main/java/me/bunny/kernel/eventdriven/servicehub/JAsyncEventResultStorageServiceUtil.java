package me.bunny.kernel.eventdriven.servicehub;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.JProperties;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;
import me.bunny.kernel.jave.reflect.JClassUtils;

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
