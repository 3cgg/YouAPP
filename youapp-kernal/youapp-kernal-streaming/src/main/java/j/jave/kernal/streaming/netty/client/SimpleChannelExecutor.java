package j.jave.kernal.streaming.netty.client;

public class SimpleChannelExecutor implements ChannelExecutor<NioChannelRunnable> {

	private NioChannelExecutor nioChannelExecutor;

	public SimpleChannelExecutor(String host,int port) {
		SimpleHttpClientInitializer httpClientInitializer=ChannelExecutors.newSimpleHttpClientInitializer(false);
		httpClientInitializer.addChannelHandler(new SimpleHttpClientHandler());
		this.nioChannelExecutor=ChannelExecutors.newNioChannelExecutor(host, port);
	}
	
	@Override
	public <V> CallPromise<V> execute(NioChannelRunnable channelRunnable) throws Exception {
		return nioChannelExecutor.execute(channelRunnable);
	}
	
	
}
