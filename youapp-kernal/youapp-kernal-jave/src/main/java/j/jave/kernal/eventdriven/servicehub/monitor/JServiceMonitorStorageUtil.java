package j.jave.kernal.eventdriven.servicehub.monitor;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.JProperties;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.reflect.JClassUtils;

public class JServiceMonitorStorageUtil implements JServiceMonitorStorage {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(JServiceMonitorStorageUtil.class);

	private static JServiceMonitorStorage serviceMonitorStorage=null;
	
	private Object sync=new Object();
	
	private JServiceMonitorStorage getServiceMonitorStorage(){
		if(serviceMonitorStorage==null){
			synchronized (sync) {
				String serviceMonitorStorageClassName=
						JConfiguration.get().getString(JProperties.SERVICE_HUB_MONITOR_STATUS_STORAGE,
						JDefaultServiceMonitorStorage.class.getName());
				try{
					serviceMonitorStorage= (JServiceMonitorStorage) JClassUtils.newObject(JClassUtils.load(serviceMonitorStorageClassName));
				}catch(Exception e){
					serviceMonitorStorageClassName=JDefaultServiceMonitorStorage.class.getName();
					serviceMonitorStorage=new JDefaultServiceMonitorStorage();
					LOGGER.warn(serviceMonitorStorageClassName +" cannot initialized, instead use default storage : "+JDefaultServiceMonitorStorage.class.getName());
				}
			}
		}
		return serviceMonitorStorage;
	}

	private static final JServiceMonitorStorageUtil INSTANCE=new JServiceMonitorStorageUtil();
	
	public static JServiceMonitorStorage get(){
		return INSTANCE;
	}
	
	@Override
	public void store(JEventProcessingStatus eventProcessingStatus) {
		getServiceMonitorStorage().store(eventProcessingStatus);
	}

	@Override
	public JEventProcessingStatus getEventProcessingStatus(String eventId) {
		return getServiceMonitorStorage().getEventProcessingStatus(eventId);
	}
	
}
