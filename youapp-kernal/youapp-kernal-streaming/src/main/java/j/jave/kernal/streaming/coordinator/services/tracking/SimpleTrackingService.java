package j.jave.kernal.streaming.coordinator.services.tracking;

import java.util.Map;

import j.jave.kernal.streaming.coordinator.WorkTracking;
import me.bunny.kernel.jave.json.JJSON;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;

public abstract class SimpleTrackingService implements TrackingService {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(SimpleTrackingService.class);

	private final Map conf;
	
	public SimpleTrackingService(Map conf) {
		this.conf=conf;
	}
	
	/* (non-Javadoc)
	 * @see j.jave.kernal.streaming.coordinator.services.tracking.TrackingService#track(j.jave.kernal.streaming.coordinator.WorkTracking)
	 */
	@Override
	public final void track(WorkTracking tracking){
		try {
			doTrack(tracking);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	protected final Map getConf() {
		return conf;
	}
	
	protected void doTrack(WorkTracking tracking) throws Exception{
		LOGGER.info(JJSON.get().formatObject(tracking));
	}
	
	
	
	
	
	
	
}
