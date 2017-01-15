package me.bunny.kernel.eventdriven.servicehub.monitor;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.JProperties;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;
import me.bunny.kernel.jave.reflect.JClassUtils;

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
