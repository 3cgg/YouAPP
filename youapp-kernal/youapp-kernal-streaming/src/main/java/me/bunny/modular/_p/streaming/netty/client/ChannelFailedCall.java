package me.bunny.modular._p.streaming.netty.client;

public interface ChannelFailedCall {

	ChannelFailedCall NOTHING=new ChannelFailedCall() {
		@Override
		public void run(Request request, Throwable cause) {
		}
	};
	
	void run(Request request,Throwable cause);
	
}
