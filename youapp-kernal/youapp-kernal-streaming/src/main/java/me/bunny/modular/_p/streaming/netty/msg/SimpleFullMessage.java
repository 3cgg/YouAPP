package me.bunny.modular._p.streaming.netty.msg;

public class SimpleFullMessage implements FullMessage {

	private Object content;
	
	private String uri;
	
	
	@Override
	public Object content() {
		return content;
	}
	
	@Override
	public MessageContent setContent(Object content) {
		this.content=content;
		return this;
	}

	@Override
	public String uri() {
		return uri;
	}
	
	@Override
	public MessageRequest setUri(String uri) {
		this.uri=uri;
		return this;
	}
	
	
}
