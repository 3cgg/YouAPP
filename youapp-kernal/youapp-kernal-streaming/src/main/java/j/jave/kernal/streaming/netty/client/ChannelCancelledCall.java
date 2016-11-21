package j.jave.kernal.streaming.netty.client;

import io.netty.channel.ChannelFuture;

public interface ChannelCancelledCall {

	ChannelCancelledCall NOTHING=new ChannelCancelledCall() {
		@Override
		public void run(ChannelFuture channelFuture) {
		}
	};
	
	void run(ChannelFuture channelFuture);
	
}
