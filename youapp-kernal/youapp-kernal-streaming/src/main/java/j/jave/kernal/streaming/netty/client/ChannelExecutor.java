package j.jave.kernal.streaming.netty.client;

public interface ChannelExecutor<T> {

	void execute(T channelRunnable);

	Object executeSync(T channelRunnable) throws InterruptedException;
}
