package j.jave.kernal.eventdriven.servicehub.monitor;

import j.jave.kernal.jave.service.JService;

public interface JServiceMonitorStorage extends JService {

	void store(JEventProcessingStatus eventProcessingStatus);
	
	JEventProcessingStatus getEventProcessingStatus(String eventId);
	
}
