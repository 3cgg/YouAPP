package j.jave.kernal.streaming.netty.msg;

public class SimpleRPCFullResponse implements RPCFullResponse {

	private Object content;
	
	
	@Override
	public Object content() {
		return content;
	}

	@Override
	public MessageContent setContent(Object content) {
		this.content=content;
		return this;
	}

}
