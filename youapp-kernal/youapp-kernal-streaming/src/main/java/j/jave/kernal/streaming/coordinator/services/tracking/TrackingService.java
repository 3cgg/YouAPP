package j.jave.kernal.streaming.coordinator.services.tracking;

import j.jave.kernal.jave.service.JService;
import j.jave.kernal.streaming.coordinator.WorkTracking;

public interface TrackingService extends JService {

	void track(WorkTracking tracking);

}