package j.jave.kernal.streaming.netty.client;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import j.jave.kernal.jave.exception.JNestedRuntimeException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;
import j.jave.kernal.streaming.zookeeper.ZooKeeperExecutorGetter;
import j.jave.kernal.streaming.zookeeper.ZooNode;
import j.jave.kernal.streaming.zookeeper.ZooNodeCallback;

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
	
	private void loadLeaderHost(String ldHostPath) {
		canService=false;
		active=null;
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
