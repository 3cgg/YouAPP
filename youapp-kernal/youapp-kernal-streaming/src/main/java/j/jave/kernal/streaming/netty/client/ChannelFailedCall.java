package j.jave.kernal.streaming.netty.client;

public interface ChannelFailedCall {

	ChannelFailedCall NOTHING=new ChannelFailedCall() {
		@Override
		public void run(Request request, Throwable cause) {
		}
	};
	
	void run(Request request,Throwable cause);
	
}
