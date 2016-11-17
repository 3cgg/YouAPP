package j.jave.kernal.streaming.netty.client;

import io.netty.util.concurrent.Future;

public interface ChannelExecutor<T> {

	<V> Future<V> execute(T channelRunnable);
	
}
