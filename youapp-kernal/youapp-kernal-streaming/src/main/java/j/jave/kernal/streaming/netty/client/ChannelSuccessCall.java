package j.jave.kernal.streaming.netty.client;

import io.netty.channel.ChannelFuture;

public interface ChannelSuccessCall {

	ChannelSuccessCall NOTHING=new ChannelSuccessCall() {
		@Override
		public void run(ChannelFuture channelFuture) {
		}
	};
	
	void run(ChannelFuture channelFuture);
	
}
