package j.jave.kernal.streaming.netty.client;

import java.io.IOException;

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
	
	boolean ssl(){
		SimpleHttpClientInitializer simpleHttpClientInitializer=(SimpleHttpClientInitializer)nioChannelExecutor.getClientInitializer();
    	return simpleHttpClientInitializer.ssl();
    }
	
	public String uri(){
		return (ssl()?"https":"http")+"://"+nioChannelExecutor.getHost()+":"+nioChannelExecutor.getPort();
	}

	@Override
	public void close() throws IOException {
		nioChannelExecutor.close();
	}
	
}
