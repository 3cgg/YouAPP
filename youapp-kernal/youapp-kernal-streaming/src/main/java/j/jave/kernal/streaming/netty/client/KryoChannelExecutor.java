package j.jave.kernal.streaming.netty.client;

public class KryoChannelExecutor implements ChannelExecutor<NioChannelRunnable> {

	private NioChannelExecutor nioChannelExecutor;

	public KryoChannelExecutor(String host,int port) {
		SimpleHttpClientInitializer httpClientInitializer=ChannelExecutors.newSimpleHttpClientInitializer(false);
		httpClientInitializer.addChannelHandler(new KryoClientHandler());
		this.nioChannelExecutor=ChannelExecutors.newNioChannelExecutor(host, port,httpClientInitializer);
	}
	
	@Override
	public <V> CallPromise<V> execute(NioChannelRunnable channelRunnable) throws Exception {
		return nioChannelExecutor.execute(channelRunnable);
	}
	
	
}
