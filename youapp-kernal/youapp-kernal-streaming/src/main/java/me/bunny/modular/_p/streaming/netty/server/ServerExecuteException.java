package me.bunny.modular._p.streaming.netty.server;

import me.bunny.kernel._c.model.JModel;

public class ServerExecuteException extends RuntimeException implements JModel{
	
	public ServerExecuteException(ErrorCode errorCode,Throwable t) {
		super(errorCode.getName(),t);
	}
	
}
