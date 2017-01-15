package me.bunny.kernel.eventdriven.servicehub.monitor;

import me.bunny.kernel._c.support.JDefaultHashCacheService;

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
