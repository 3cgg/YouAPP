package j.jave.kernal.streaming.netty.client;

public interface ChannelResponseCall {

	ChannelResponseCall NOTHING=new ChannelResponseCall() {
		@Override
		public void run(Request request, Object object) {
		}
	};
	
	void run(Request request,Object object);
	
}
