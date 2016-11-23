package j.jave.kernal.streaming.netty.client;

import java.util.concurrent.Executors;

import j.jave.kernal.jave.exception.JNestedRuntimeException;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.streaming.zookeeper.JNode;
import j.jave.kernal.streaming.zookeeper.JZooKeeperConnector;
import j.jave.kernal.streaming.zookeeper.JZooKeeperConnector.ZookeeperExecutor;
import j.jave.kernal.streaming.zookeeper.ZooKeeperExecutorGetter;

public abstract class DynamicChannelExecutor implements ChannelExecutor<NioChannelRunnable> {

	private KryoChannelExecutor active;
	
	private boolean canService=true;
	
	private String host;
	
	private int port;
	
	private ZookeeperExecutor executor=ZooKeeperExecutorGetter.getDefault();
	
	public DynamicChannelExecutor() {
		executor.watchPath("/leader-host", new JZooKeeperConnector.NodeCallback() {
			@Override
			public void call(JNode node) {
				canService=false;
				active=null;
				String led=JStringUtils.utf8(executor.getPath(node.getPath()));
				String[] ld=led.split(":");
				host=ld[0];
				port=Integer.parseInt(ld[1]);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try{
							if(active==null){
								synchronized (this) {
									if(active==null){
										active=doGetActive(host, port);
										wakeup();
									}
								}
							}
						}catch (Exception e) {
							throw new JNestedRuntimeException(e);
						}
					}
				}).start();
			}
		},Executors.newFixedThreadPool(1));
		
	}
	
	private synchronized void wakeup(){
		notifyAll();
	}
	
	final KryoChannelExecutor getActive() {
		return active;
	}
	
	abstract protected KryoChannelExecutor doGetActive(String host,int port) throws Exception;
	

	private void canService(){
		if(!canService){
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public <V> CallPromise<V> execute(NioChannelRunnable channelRunnable) throws Exception {
		canService();
		return getActive().execute(channelRunnable);
	}

	@Override
	public String uri() {
		canService();
		return getActive().uri();
	}

}
