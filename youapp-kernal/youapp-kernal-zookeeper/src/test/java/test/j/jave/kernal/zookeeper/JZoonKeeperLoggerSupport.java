package test.j.jave.kernal.zookeeper;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;

import org.apache.zookeeper.WatchedEvent;

public class JZoonKeeperLoggerSupport {

	protected static final JLogger LOGGER=JLoggerFactory.getLogger(JZoonKeeperLoggerSupport.class);
	
	public static void loggerWatchedEvent(WatchedEvent watchedEvent,String prefix){
		LOGGER.info(prefix+" : "+JJSON.get().formatObject(watchedEvent));
	}
	
	public static void loggerWatchedEvent(WatchedEvent watchedEvent){
		loggerWatchedEvent(watchedEvent, "DEFAULT");
	}
	
}
