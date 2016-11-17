package j.jave.kernal.streaming.netty.client;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;

public class NioChannelRunnable extends ChannelRunnable {
	
	public NioChannelRunnable(Request request) {
		super(request);
	}
	
	@Override
	protected void doRun(Channel channel, DefaultFullHttpRequest fullHttpRequest) throws Exception {
		channel.writeAndFlush(fullHttpRequest);
	}
	
	
	
}
