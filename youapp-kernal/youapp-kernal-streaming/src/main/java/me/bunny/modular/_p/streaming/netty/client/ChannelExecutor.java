package me.bunny.modular._p.streaming.netty.client;

import java.io.Closeable;

public interface ChannelExecutor<T> extends Closeable {

	<V> CallPromise<V> execute(T channelRunnable) throws Exception;

	String uri();
}
