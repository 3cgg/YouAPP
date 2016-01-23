package j.jave.framework.zookeeper.test;

import j.jave.framework.commons.json.JJSON;
import j.jave.framework.commons.logging.JLogger;
import j.jave.framework.commons.logging.JLoggerFactory;

import org.apache.zookeeper.WatchedEvent;

public class JZoonKeeperLoggerSupport {

	protected static final JLogger LOGGER=JLoggerFactory.getLogger(JZoonKeeperLoggerSupport.class);
	
	public static void loggerWatchedEvent(WatchedEvent watchedEvent,String prefix){
		LOGGER.info(prefix+" : "+JJSON.get().format(watchedEvent));
	}
	
	public static void loggerWatchedEvent(WatchedEvent watchedEvent){
		loggerWatchedEvent(watchedEvent, "DEFAULT");
	}
	
}
