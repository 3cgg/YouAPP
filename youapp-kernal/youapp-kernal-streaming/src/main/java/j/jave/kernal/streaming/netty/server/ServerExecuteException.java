package j.jave.kernal.streaming.netty.server;

import j.jave.kernal.jave.model.JModel;

public class ServerExecuteException extends RuntimeException implements JModel{
	
	public ServerExecuteException(ErrorCode errorCode,Throwable t) {
		super(errorCode.getName(),t);
	}
	
}
