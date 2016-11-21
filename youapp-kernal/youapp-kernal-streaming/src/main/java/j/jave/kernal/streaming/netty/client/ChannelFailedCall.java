package j.jave.kernal.streaming.netty.client;

import io.netty.channel.ChannelFuture;

public interface ChannelFailedCall {

	ChannelFailedCall NOTHING=new ChannelFailedCall() {
		@Override
		public void run(ChannelFuture channelFuture) {
		}
	};
	
	void run(ChannelFuture channelFuture);
	
}
