package me.bunny.modular._p.streaming.coordinator.services.tracking;

import me.bunny.kernel._c.service.JService;
import me.bunny.modular._p.streaming.coordinator.WorkTracking;

public interface TrackingService extends JService {

	void track(WorkTracking tracking);

}