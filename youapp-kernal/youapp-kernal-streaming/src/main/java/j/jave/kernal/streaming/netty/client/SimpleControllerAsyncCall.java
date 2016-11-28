package j.jave.kernal.streaming.netty.client;

import java.lang.reflect.Method;

import j.jave.kernal.jave.exception.JNestedRuntimeException;

public class SimpleControllerAsyncCall implements ControllerAsyncCall {

	@Override
	public void success(Object proxy, Method method, Object[] args, Object returnVal) {
	}

	@Override
	public void fail(Object proxy, Method method, Object[] args, Throwable throwable) {
		throw new JNestedRuntimeException(throwable);
	}

}
