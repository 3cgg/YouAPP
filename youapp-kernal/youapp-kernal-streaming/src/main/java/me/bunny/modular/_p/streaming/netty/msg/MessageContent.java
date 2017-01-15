package me.bunny.modular._p.streaming.netty.msg;

public interface MessageContent {

	Object content();
	
	MessageContent setContent(Object content);
	
}
