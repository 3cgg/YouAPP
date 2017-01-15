package j.jave.kernal.streaming.coordinator.services.tracking;

import j.jave.kernal.streaming.coordinator.WorkTracking;
import me.bunny.kernel._c.service.JService;

public interface TrackingService extends JService {

	void track(WorkTracking tracking);

}