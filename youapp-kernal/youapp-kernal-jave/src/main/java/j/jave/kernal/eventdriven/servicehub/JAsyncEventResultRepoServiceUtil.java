package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.JProperties;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.reflect.JClassUtils;

public class JAsyncEventResultRepoServiceUtil implements JAsyncEventResultRepoService {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(JAsyncEventResultRepoServiceUtil.class);
	
	private JAsyncEventResultRepoService asyncEventResultRepoService;
	
	private final Object sync=new Object();
	
	private JAsyncEventResultRepoService getRepoService() {
		
		if(asyncEventResultRepoService==null){
			synchronized (sync) {
				if(asyncEventResultRepoService==null){
					String repoService=JConfiguration.get().getString(JProperties.ASYNC_EVENT_RESULT_REPO_SERVICE, JDefaultAsyncEventResultRepoService.class.getName());
					if(JDefaultAsyncEventResultRepoService.class.getName().equals(repoService)){
						asyncEventResultRepoService=JServiceHubDelegate.get().getActualService(this, JDefaultAsyncEventResultRepoService.class);;
					}
					else{
						try{
							Class<JAsyncEventResultRepoService> clazz=JClassUtils.load(repoService);
							asyncEventResultRepoService=JServiceHubDelegate.get().getActualService(this, clazz);
						}catch(Exception e){
							LOGGER.info(e.getMessage(), e);
							asyncEventResultRepoService=JServiceHubDelegate.get().getActualService(this, JDefaultAsyncEventResultRepoService.class);;
							LOGGER.warn(repoService +" cannot found in the service hub, instead use default repo service : "+JDefaultAsyncEventResultRepoService.class.getName());
						}
					}
				
				
				}
			}
		}
		return asyncEventResultRepoService;
	}
	
	private JAsyncEventResultRepoServiceUtil() {}
	
	private static final JAsyncEventResultRepoServiceUtil instance=new JAsyncEventResultRepoServiceUtil();
	
	public static final JAsyncEventResultRepoService get(){
		return instance;
	}
	
	@Override
	public Object getEventResult(String eventUnique)
			throws JEventExecutionException {
		return getRepoService().getEventResult(eventUnique);
	}

	@Override
	public void putEventResult(JEventExecution eventExecution) {
		getRepoService().putEventResult(eventExecution);
	}
	
	
}
