package j.jave.kernal.streaming.netty.client;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

import com.google.common.collect.Maps;

public class KryoChannelExecutorPool implements Closeable {

	private Map<String, KryoChannelExecutor> executors=Maps.newHashMap();
	
	private String key(String host,int port){
		return host+":"+port;
	}
	
	private final Object sync=new Object();

	public KryoChannelExecutor get(String host,int port){
		String key=key(host, port);
		KryoChannelExecutor executor=executors.get(key);
		if(executor==null){
			synchronized (sync) {
				executor=executors.get(key);
				if(executor==null){
					executor=new KryoChannelExecutor(host, port);
					executors.put(key, executor);
				}
			}
		}
		return executor;
	}
	
	@Override
	public void close() throws IOException {
		for(KryoChannelExecutor executor:executors.values()){
			executor.close();
		}
	}
	
	
}
