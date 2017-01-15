package me.bunny.modular._p.streaming.coordinator.services.tracking;

import java.util.Map;

public class TrackingServiceFactory {

	public static TrackingService build(Map conf){
		return new KafkaTrackingService(conf);
	}
	
}
