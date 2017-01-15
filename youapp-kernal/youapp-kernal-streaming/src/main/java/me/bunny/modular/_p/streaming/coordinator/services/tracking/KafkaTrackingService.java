package me.bunny.modular._p.streaming.coordinator.services.tracking;

import java.util.Map;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.modular._p.streaming.ConfigNames;
import me.bunny.modular._p.streaming.coordinator.WorkTracking;
import me.bunny.modular._p.streaming.kafka.KafkaProducerConfig;
import me.bunny.modular._p.streaming.kafka.ProducerConnector;
import me.bunny.modular._p.streaming.kafka.SimpleProducer;
import me.bunny.modular._p.streaming.kafka.ProducerConnector.ProducerExecutor;

public class KafkaTrackingService implements TrackingService {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(KafkaTrackingService.class);

	private static String DEFAULT_TOPIC="";
	
	private final Map conf;
	
	private final SimpleProducer simpleProducer;
	
	public KafkaTrackingService(Map conf) {
		this.conf=conf;
		KafkaProducerConfig producerConfig=KafkaProducerConfig.build(this.conf);
		ProducerConnector producerConnecter=new ProducerConnector(producerConfig);
		ProducerExecutor<String,Object> producerExecutor=  producerConnecter.connect();
		simpleProducer =new SimpleProducer(producerExecutor);
		DEFAULT_TOPIC=JConfiguration.newInstance().getString(
				ConfigNames.STREAMING_WORKFLOW_TRACKING_KAFKA_TOPIC);
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
		simpleProducer.send(tracking,_topic(tracking));
	}
	
	private String _topic(WorkTracking tracking){
		return JStringUtils.isNullOrEmpty(tracking.getTrackingTopic())?DEFAULT_TOPIC
				:tracking.getTrackingTopic();
	}
	
	
	
	
	
}
