package me.bunny.modular._p.streaming.netty.client;

public interface ChannelCancelledCall {

	ChannelCancelledCall NOTHING=new ChannelCancelledCall() {
		@Override
		public void run(Request request, Object cause) {
		}
	};
	
	void run(Request request,Object cause);
	
}
