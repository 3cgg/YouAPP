package j.jave.kernal.zookeeper;

import java.util.List;

import org.apache.zookeeper.AsyncCallback.Children2Callback;
import org.apache.zookeeper.data.Stat;

public abstract class JChildren2Callback implements Children2Callback {

	@Override
	public void processResult(int rc, String path, Object ctx,
			List<String> children, Stat stat) {
		doProcessResult(rc, path, ctx, children, stat);
	}
	protected abstract void doProcessResult(int rc, String path, Object ctx,
			List<String> children, Stat stat);
	
}
