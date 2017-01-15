package me.bunny.kernel.eventdriven.servicehub.monitor;

import me.bunny.kernel.JProperties;
import me.bunny.kernel._c.service.JService;

/**
 * store any monitoring model, if any other custom one ( XML configuration) is not found, use {@link JDefaultServiceMonitorStorage} ad default.
 * @author J
 * @see JDefaultServiceMonitorStorage
 * @see JProperties#SERVICE_HUB_MONITOR_STATUS_STORAGE
 */
public interface JServiceMonitorStorage extends JService {

	void store(JEventProcessingStatus eventProcessingStatus);
	
	JEventProcessingStatus getEventProcessingStatus(String eventId);
	
}
