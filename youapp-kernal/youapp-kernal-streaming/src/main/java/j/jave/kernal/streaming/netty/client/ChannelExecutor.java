package j.jave.kernal.streaming.netty.client;

public interface ChannelExecutor<T> {

	<V> CallPromise<V> execute(T channelRunnable) throws Exception;

	String uri();
}
