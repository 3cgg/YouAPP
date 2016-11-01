package j.jave.kernal.streaming.coordinator;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.zookeeper.CreateMode;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.kernal.streaming.coordinator.NodeData.NodeStatus;
import j.jave.kernal.streaming.coordinator.command.WorkerTemporary;
import j.jave.kernal.streaming.kafka.JKafkaProducerConfig;
import j.jave.kernal.streaming.kafka.JProducerConnector;
import j.jave.kernal.streaming.kafka.JProducerConnector.ProducerExecutor;
import j.jave.kernal.streaming.kafka.SimpleProducer;
import j.jave.kernal.streaming.zookeeper.JNode;
import j.jave.kernal.streaming.zookeeper.JZooKeeperConnector;
import j.jave.kernal.streaming.zookeeper.JZooKeeperConnector.ZookeeperExecutor;


@SuppressWarnings("serial")
public class NodeWorker implements Serializable {

	private Map conf;
	
	private String prefix="worker-";
	
	private String logPrefix;
	
	private int id;
	
	public String name;
	
	private WorkflowMeta workflowMeta;
	
	private ZookeeperExecutor executor;

	private LeaderLatch processorLeaderLatch;
	
	private WorkerTemporary workerTemporary;
	
	private Master master;
	
	private SimpleProducer simpleProducer;
	
	private ExecutorService executorService=Executors.newFixedThreadPool(3);
	
	public NodeWorker(int id, String workerName,WorkflowMeta workflowMeta,ZookeeperExecutor executor,Map conf) {
		this.id = id;
		this.name = workerName;
		this.logPrefix="(worker["+workerName+"])+"+id+" ";
		this.workflowMeta=workflowMeta;
		this.conf=conf;
		JKafkaProducerConfig producerConfig=JKafkaProducerConfig.build(this.conf);
		JProducerConnector producerConnecter=new JProducerConnector(producerConfig);
		ProducerExecutor<String,String> producerExecutor=  producerConnecter.connect();
		simpleProducer =new SimpleProducer(producerExecutor, 
				"workflow-instance-track");
		this.executor=executor;
		processorLeaderLatch=new LeaderLatch(executor.backend(),
				processorLeaderPath());
		processorLeaderLatch.addListener(new LeaderLatchListener() {
			
			@Override
			public void notLeader() {
				if(master==null) return;
				System.out.println(logPrefix+"(Thread)+"+Thread.currentThread().getName()+" lose worker-processor eadership .... ");
				if(master.processorsWather!=null){
					try {
						master.processorsWather.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				master=null;
			}
			
			@Override
			public void isLeader() {
				System.out.println(logPrefix+"(Thread)+"+Thread.currentThread().getName()+" is worker-processor eadership .... ");
				createMasterMeta();
			}
		}, Executors.newFixedThreadPool(1));
		
		startWorker();
	}

	private String pluginWorkerPath(){
		String pluginWorkersPath= CoordinatorPaths.BASE_PATH
				+"/pluginWorkers/"+workflowMeta.getName()+"-workers";
		return pluginWorkersPath+"/"+prefix+String.valueOf(id);
	}
	
	private class Master{
		
		PathChildrenCache processorsWather;
		
	}
	
	private void startWorker(){
		final String path=pluginWorkerPath();
		SingleMonitor singleMonitor=SingleMonitor.get(
				CoordinatorPaths.BASE_PATH
				+"/worker-register-sync-lock/"+workflowMeta.getName()+"/"+id); 
		try{
			singleMonitor.acquire();
			if(!executor.exists(path)){
				WorkerPathVal workerPathVal=new WorkerPathVal();
				workerPathVal.setId(id);
				workerPathVal.setTime(new Date().getTime());
				executor.createPath(path,JJSON.get().formatObject(workerPathVal).getBytes(Charset.forName("utf-8")),CreateMode.PERSISTENT);
			}
			System.out.println(logPrefix+"  add wahter on : "+path);
			executor.watchPath(path, new JZooKeeperConnector.NodeCallback () {
				@Override
				public void call(JNode node) {
					try{
						final WorkerPathVal workerPathVal=
								JJSON.get().parse(node.getStringData(),WorkerPathVal.class);
						final String tempPath=executor.createEphSequencePath(path+"/temp-");
						WorkerTemporary workerTemporary=new WorkerTemporary();
						workerTemporary.setTempPath(tempPath);
						workerTemporary.setWorkerPathVal(workerPathVal);
						setWorkerTemporary(workerTemporary);
						executorService.execute(new Runnable() {
							@Override
							public void run() {
								WorkTracking workTracking=
										workTracking(workerPathVal.getId(), 
												workerPathVal.getSequence(), tempPath,
												NodeStatus.PROCESSING);
								simpleProducer.send(workTracking);
							}
						});
						wakeup();
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
			},Executors.newFixedThreadPool(1, new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					return new Thread(r, pluginWorkerPath());
				}
			}));
			
			Executors.newFixedThreadPool(1, new ThreadFactory() {
				
				@Override
				public Thread newThread(Runnable r) {
					return new Thread(r, path+"{watch children(leader)}");
				}
			}).execute(new Runnable() {
				
				@Override
				public void run() {
					try{
						processorLeaderLatch.start();
						processorLeaderLatch.await();
						createMasterMeta();
						attachPluginWorkerPathWatcher(path);
						
						while(true){
							try{
								synchronized (this) {
									wait();
								}
							}catch (InterruptedException e) {
							}
						}
						
					}catch (Exception e) {
						e.printStackTrace();
					}finally {
						try {
							processorLeaderLatch.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				singleMonitor.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private synchronized void createMasterMeta(){
		if(master!=null) return;
		master=new Master();
	}
	
	
	private void attachPluginWorkerPathWatcher(final String path){
		final PathChildrenCache cache= executor.watchChildrenPath(path, new JZooKeeperConnector.NodeChildrenCallback() {
			@Override
			public void call(List<JNode> nodes) {
				if(nodes.isEmpty()){
					WorkerPathVal workerPathVal= workerPathVal();
					complete(workerPathVal.getInstancePath());
				}
			}
		}, Executors.newFixedThreadPool(1, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, path+"{cache:watch children}");
			}
		}),PathChildrenCacheEvent.Type.CHILD_REMOVED);
		master.processorsWather=cache;
	}
	
	private void complete(final String path){
		final InstanceNodeVal instanceNodeVal=JJSON.get().parse(new String(executor.getPath(path),Charset.forName("utf-8")), InstanceNodeVal.class);
		instanceNodeVal.setStatus(NodeStatus.COMPLETE);
		executor.setPath(path, JJSON.get().formatObject(instanceNodeVal));
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				WorkTracking workTracking=
						workTracking(instanceNodeVal.getId(), instanceNodeVal.getSequence(), path,
								NodeStatus.COMPLETE);
				simpleProducer.send(workTracking);
			}
		});
	}
	
	
	private WorkTracking workTracking(int workerId,long instanceId,String path,String status){
		WorkTracking workTracking=new WorkTracking();
		workTracking.setWorkerId(String.valueOf(workerId));
		workTracking.setInstancePath(path);
		workTracking.setStatus(status);
		workTracking.setRecordTime(new Date().getTime());
		workTracking.setWorkerName(this.name);
		workTracking.setId(JUniqueUtils.unique());
		workTracking.setOffset(-1);
		workTracking.setHashKey(String.valueOf(instanceId));
		return workTracking;
	}
	
	private WorkerPathVal workerPathVal(){
		byte[] bytes= executor.getPath(pluginWorkerPath());
		return JJSON.get().parse(new String(bytes,Charset.forName("utf-8")),WorkerPathVal.class); 
	}
	
	public int getId() {
		return id;
	}
	
	String processorLeaderPath(){
		return CoordinatorPaths.BASE_PATH
				+"/worker-processor-leader-latch/"+workflowMeta.getName()+"/"+id;
	}
	
	public void acquire() throws Exception{
		
		while(true){
			try{
				synchronized (this) {
					System.out.println(logPrefix+" go to wait .... ");
					wait();
					System.out.println(logPrefix+" waked up ... ");
					break;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public boolean acquire(long time, TimeUnit unit) throws Exception{
		int count=0;
		while(true){
			try{
				if(count>3){
					break;
				}
				synchronized (this) {
					try{
						wait(unit.toMillis(time));
						return true;
					}catch (Exception e) {
						count++;
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		throw new TimeoutException(" worker cannot be scheduled.");
	}
	
	private void setWorkerTemporary(WorkerTemporary workerTemporary) {
		this.workerTemporary = workerTemporary;
	}
	
	
	public void release() throws Exception{
		release(null);
	}
	
	public void release(String errorMessage) throws Exception{
		System.out.println(logPrefix+" delete temp path : "+workerTemporary.getTempPath());
		executor.deletePath(workerTemporary.getTempPath());
	}
	
	private void wakeup(){
		synchronized (this) {
			notifyAll();
		}
	}
	
}
