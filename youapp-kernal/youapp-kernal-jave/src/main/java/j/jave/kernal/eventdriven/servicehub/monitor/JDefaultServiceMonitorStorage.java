package j.jave.kernal.eventdriven.servicehub.monitor;

import j.jave.kernal.jave.support.JDefaultHashCacheService;

public class JDefaultServiceMonitorStorage implements JServiceMonitorStorage{

	private JDefaultHashCacheService defaultHashCacheService=new JDefaultHashCacheService();
	
	@Override
	public void store(JEventProcessingStatus eventProcessingStatus) {
		defaultHashCacheService.putNeverExpired(eventProcessingStatus.getUnique(), eventProcessingStatus);
	}

	@Override
	public JEventProcessingStatus getEventProcessingStatus(String eventId) {
		return (JEventProcessingStatus) defaultHashCacheService.get(eventId);
	}
	
}
