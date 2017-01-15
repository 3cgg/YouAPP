package me.bunny.modular._p.streaming.netty.client;

import java.lang.reflect.Method;

import me.bunny.kernel._c.exception.JNestedRuntimeException;

public class SimpleControllerAsyncCall implements ControllerAsyncCall {

	@Override
	public void success(Object proxy, Method method, Object[] args, Object returnVal) {
	}

	@Override
	public void fail(Object proxy, Method method, Object[] args, Throwable throwable) {
		throw new JNestedRuntimeException(throwable);
	}

}
