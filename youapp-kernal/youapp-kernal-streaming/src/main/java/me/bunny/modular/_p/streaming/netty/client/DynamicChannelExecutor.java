package me.bunny.modular._p.streaming.netty.client;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import me.bunny.kernel._c.exception.JNestedRuntimeException;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.modular._p.streaming.zookeeper.ZooKeeperExecutorGetter;
import me.bunny.modular._p.streaming.zookeeper.ZooNode;
import me.bunny.modular._p.streaming.zookeeper.ZooNodeCallback;
import me.bunny.modular._p.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

public abstract class DynamicChannelExecutor implements ChannelExecutor<NioChannelRunnable> {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(DynamicChannelExecutor.class);
	
	private ChannelExecutor<NioChannelRunnable> active;
	
	private boolean canService=false;
	
	private String host;
	
	private int port;
	
	private ZookeeperExecutor executor=ZooKeeperExecutorGetter.getDefault();
	
	private ScheduledExecutorService scheduledExecutorService=Executors.newScheduledThreadPool(1);
	
	public DynamicChannelExecutor(String znodePath) {
		final String ldHostPath=znodePath;
		scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				if(executor.exists(ldHostPath)){
					loadLeaderHost(ldHostPath);
					executor.watchPath(ldHostPath, new ZooNodeCallback() {
						@Override
						public void call(ZooNode node) {
							loadLeaderHost(ldHostPath);
						}
					},Executors.newFixedThreadPool(1));
					scheduledExecutorService.shutdown();
				}else{
					LOGGER.info("waiting ["+ldHostPath+"] registered in zookeeper."); 
				}
			}
		}, 1,10, TimeUnit.SECONDS);
	}
	
	private void _closeActive(){
		if(active!=null){
			try {
				active.close();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		active=null;
		host=null;
		port=-1;
	}
	
	private void loadLeaderHost(String ldHostPath) {
		canService=false;
		_closeActive();
		String led=JStringUtils.utf8(executor.getPath(ldHostPath));
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
	
	private synchronized void wakeup(){
		canService=true;
		notifyAll();
	}
	
	final ChannelExecutor<NioChannelRunnable> getActive() {
		return active;
	}
	
	abstract protected ChannelExecutor<NioChannelRunnable> doGetActive(String host,int port) throws Exception;
	

	private void canService(){
		if(!canService){
			synchronized (this) {
				try {
					LOGGER.info("connection is not ready, wait...");
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

	@Override
	public void close() throws IOException {
		canService();
		getActive().close();
	}
}
