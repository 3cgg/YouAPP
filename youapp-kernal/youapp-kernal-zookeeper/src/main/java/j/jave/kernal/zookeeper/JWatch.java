package j.jave.kernal.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public abstract class JWatch implements Watcher{

	@Override
	public final void process(WatchedEvent event) {
		doProcess(event);
	}

	protected abstract void doProcess(WatchedEvent event);
	
	
}
