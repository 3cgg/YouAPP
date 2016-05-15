package j.jave.kernal.eventdriven.servicehub.monitor;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.JProperties;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.reflect.JClassUtils;

public class JServiceMonitorStorageUtil {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(JServiceMonitorStorageUtil.class);

	private static JServiceMonitorStorage serviceMonitorStorage=null;
	
	public synchronized static JServiceMonitorStorage getServiceMonitorStorage(){
		String serviceMonitorStorageClassName=null;
		if(serviceMonitorStorage==null){
			serviceMonitorStorageClassName=JConfiguration.get().getString(JProperties.SERVICE_HUB_MONITOR_STATUS_STORAGE,
					JDefaultServiceMonitorStorage.class.getName());
			try{
				serviceMonitorStorage= (JServiceMonitorStorage) JClassUtils.newObject(JClassUtils.load(serviceMonitorStorageClassName));
			}catch(Exception e){
				serviceMonitorStorage=new JDefaultServiceMonitorStorage();
				LOGGER.warn(serviceMonitorStorageClassName +" cannot initialized, instead use default storage : "+JDefaultServiceMonitorStorage.class.getName());
			}
		}
		return serviceMonitorStorage;
	}
	
	
}
