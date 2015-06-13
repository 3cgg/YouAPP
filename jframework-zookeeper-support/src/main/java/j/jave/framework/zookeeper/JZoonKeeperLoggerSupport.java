package j.jave.framework.zookeeper;

import j.jave.framework.extension.logger.JLogger;
import j.jave.framework.json.JJSON;
import j.jave.framework.logging.JLoggerFactory;

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
