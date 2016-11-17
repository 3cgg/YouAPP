package j.jave.kernal.streaming.netty.client;

public class ChannelExecutors {

	public static NioChannelExecutor newNioChannelExecutor(String host, int port){
		return newNioChannelExecutor(host, port, false);
	}

	public static NioChannelExecutor newNioChannelExecutor(String host, int port, boolean useSSL){
		return new NioChannelExecutor(host, port, useSSL);
	}
	
}
