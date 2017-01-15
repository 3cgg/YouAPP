package me.bunny.kernel.http;

import me.bunny.kernel._c.extension.JIFactory;

public interface JIHttpFactory extends JIFactory {

	JHttpBase<?> getHttpPost();
	
	JHttpBase<?> getHttpGet();
	
	JHttpBase<?> getHttpPut();
	
	JHttpBase<?> getHttpDelete();
}
