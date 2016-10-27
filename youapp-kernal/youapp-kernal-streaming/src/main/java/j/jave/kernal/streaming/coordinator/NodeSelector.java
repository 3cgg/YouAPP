package j.jave.kernal.streaming.coordinator;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.kernal.streaming.coordinator.NodeData.NodeStatus;
import j.jave.kernal.streaming.kafka.JKafkaProducerConfig;
import j.jave.kernal.streaming.kafka.JProducerConnecter;
import j.jave.kernal.streaming.kafka.JProducerConnecter.ProducerExecutor;
import j.jave.kernal.streaming.kafka.SimpleProducer;
import j.jave.kernal.streaming.zookeeper.JNode;
import j.jave.kernal.streaming.zookeeper.JZooKeeperConnecter;
import j.jave.kernal.streaming.zookeeper.JZooKeeperConnecter.ZookeeperExecutor;

@SuppressWarnings({"serial","rawtypes"})
public class NodeSelector implements Serializable{
	
	private Map conf;
	
	private String name;
	
	private String logPrefix;
	
	private String basePath=CoordinatorPaths.BASE_PATH;
	
	private ZookeeperExecutor executor;
	
	private LeaderLatch leaderLatch;
	
	private DistAtomicLong atomicLong;

	private WorkflowMaster workflowMaster;
	
	private SimpleProducer simpleProducer;
	
	private ExecutorService executorService=Executors.newFixedThreadPool(3);
	
	public static abstract class CacheType{
		private static final String CHILD="patch_child";
	}
	
	private static NodeSelector NODE_SELECTOR;
	
	public synchronized static NodeSelector startup(String name,ZookeeperExecutor executor,Map conf){
		NodeSelector nodeSelector=NODE_SELECTOR;
		if(nodeSelector==null){
			nodeSelector=new NodeSelector(executor);
			nodeSelector.conf=conf;
			nodeSelector.name=name;
			nodeSelector.logPrefix="selector["+nodeSelector.name+"] ";
			JKafkaProducerConfig producerConfig=JKafkaProducerConfig.build(conf);
			JProducerConnecter producerConnecter=new JProducerConnecter(producerConfig);
			ProducerExecutor<String,String> producerExecutor=  producerConnecter.connect();
			SimpleProducer simpleProducer =new SimpleProducer(producerExecutor, 
					"workflow-instance-track");
			nodeSelector.simpleProducer=simpleProducer;
			NODE_SELECTOR=nodeSelector;
		}
		return NODE_SELECTOR;
	}
	
	private NodeSelector(ZookeeperExecutor executor) {
		this.executor = executor;
		this.atomicLong=new DistAtomicLong(executor);
		leaderLatch=new LeaderLatch(executor.backend(),
				leaderPath());
		leaderLatch.addListener(new LeaderLatchListener() {
			
			@Override
			public void notLeader() {
				if(workflowMaster==null) return;
				System.out.println(logPrefix()+" lose leadership .... ");
				try {
					workflowMaster.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				workflowMaster=null;
			}
			
			@Override
			public void isLeader() {
				System.out.println(logPrefix()+" is leadership .... ");
				createMasterMeta();
			}
		}, Executors.newFixedThreadPool(1));
		
		Executors.newFixedThreadPool(1, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "worker-selector");
			}
		}).execute(new Runnable() {
			@Override
			public void run() {
				startLeader();
			}
		});
	}

	String workflowPath(final Workflow workflow){
		return workflow==null?basePath:(basePath+"/"+workflow.getName());
	}
	
	String pluginWorkersPath(final Workflow workflow){
		return basePath+"/pluginWorkers/"+workflow.getName()+"-workers";
	}
	
	String instancePath(String path,Instance instance){
		return basePath+"/instance/"+instance.getWorkflow().getName()+"/"+instance.getSequence()+path;
	}
	
	String workflowTrigger(final Workflow workflow){
		String triggerPath=basePath+"/workflow-trigger";
		return workflow==null?triggerPath:(triggerPath+"/"+workflow.getName());
	}
	
	String workflowAddPath(){
		return basePath+"/workers-collection-repo";
	}
	
	String leaderPath(){
		return basePath+"/leader-latch";
	}
	
	String basePath(){
		return basePath;
	}
	
	
	String simpleTrackingPath(){
		return basePath+"-tasks-tracking/sequence";
	}
	
	private long getSequence(){
		return atomicLong.getSequence();
	}
	
	private synchronized void addWorkflow(WorkflowMeta workflowMeta){
//		if(!workflowMaster.existsWorkflow(workflowMeta.getName())){
			Workflow workflow=workflowMaster.getWorkflow(workflowMeta.getName());
			if(workflow==null){
				workflow=new Workflow(workflowMeta.getName());
				workflow.setNodeData(workflowMeta.getNodeData());
				workflowMaster.addWorkflow(workflow);
				attachWorkersPathWatcher(workflow);
			}
			else{
				workflow.setNodeData(workflowMeta.getNodeData());
			}
//		}
	}
	
	
	
	
	private void close(long sequence,String path,String cacheType){
		if(path!=null){
			try {
				InstanceNode instanceNode=workflowMaster.getInstances().get(sequence).getInstanceNodes().get(path);
				if(CacheType.CHILD.equals(cacheType)){
					instanceNode.getPathChildrenCache().close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void createInstancePath(NodeData c,Instance instance){
		if(c!=null){
			String instancePath=instancePath(c.getPath(),instance);
			InstanceNodeVal instanceNodeVal=new InstanceNodeVal();
			instanceNodeVal.setId(c.getId());
			instanceNodeVal.setParallel(c.getParallel());
			instanceNodeVal.setStatus(NodeStatus.ONLINE);
			instanceNodeVal.setSequence(instance.getSequence());
			instanceNodeVal.setTime(new Date().getTime());
			executor.createPath(instancePath
					,JJSON.get().formatObject(instanceNodeVal).getBytes(Charset.forName("utf-8")));
			
			InstanceNode instanceNode=new InstanceNode();
			instanceNode.setSequence(instance.getSequence());
			instanceNode.setPath(instancePath);
			instanceNode.setInstanceNodeVal(instanceNodeVal);
			instanceNode.setId(c.getId());
			instanceNode.setNodeData(c);
			instance.addInstanceNode(instancePath, instanceNode);
			
			if(c.hasChildren()){
				instance.addChildPathWatcherPath(instancePath);
				for(NodeData data:c.getNodes()){
					createInstancePath(data, instance);
				}
			}else{
				instance.addWorkerPath(instancePath);
			}
		}
	}
	
	private void complete(final String path){
		final InstanceNodeVal instanceNodeVal=JJSON.get().parse(new String(executor.getPath(path),Charset.forName("utf-8")), InstanceNodeVal.class);
		instanceNodeVal.setStatus(NodeStatus.COMPLETE);
		instanceNodeVal.setTime(new Date().getTime());
		executor.setPath(path, JJSON.get().formatObject(instanceNodeVal));
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				Instance instance=workflowMaster.getInstance(instanceNodeVal.getSequence());
				WorkTracking workTracking=
						workTracking(instanceNodeVal.getId(), path, NodeStatus.COMPLETE, instance);
				simpleProducer.send(workTracking);
			}
		});
	}
	
	private WorkTracking workTracking(int workerId, String path,String status,Instance instance){
		WorkTracking workTracking=new WorkTracking();
		workTracking.setWorkerId(String.valueOf(workerId));
		workTracking.setInstancePath(path);
		workTracking.setStatus(status);
		workTracking.setRecordTime(new Date().getTime());
		InstanceNode instanceNode=instance.getInstanceNode(path);
		workTracking.setWorkerName(instanceNode.getNodeData().getName());
		workTracking.setId(JUniqueUtils.unique());
		workTracking.setOffset(-1);
		workTracking.setHashKey(String.valueOf(instance.getSequence()));
		return workTracking;
	}
	
	private int pathSequence(String path){
		String lastStr=path.substring(path.lastIndexOf("/"));
		return Integer.parseInt(lastStr.substring(lastStr.lastIndexOf("-")+1));
	}
	
	private void start(final int worker,final String instancePath,final Instance instance){
		final WorkerPathVal workerPathVal=new WorkerPathVal();
		workerPathVal.setId(worker);
		workerPathVal.setTime(new Date().getTime());
		workerPathVal.setSequence(instance.getSequence());
		workerPathVal.setInstancePath(instancePath);
		executor.setPath(instance.getWorkflow().getWorkerPaths().get(worker),
				JJSON.get().formatObject(workerPathVal));
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				WorkTracking workTracking=
						workTracking(worker, instancePath, NodeStatus.READY, instance);
				simpleProducer.send(workTracking);
			}
		});
		
	}
	
	private void start(Instance instance){
		propagateWorkerPath(null, null, instance.getWorkflow().getNodeData(), instance);
	}
	
	private void attachInstanceChildPathWatcher(final Instance instance){
		for(String path:instance.getChildPathWatcherPaths()){
			final String _path=path;
			InstanceNode instanceNode=instance.getInstanceNodes().get(_path);
			final PathChildrenCache cache= executor.watchChildrenPath(_path, new JZooKeeperConnecter.NodeChildrenCallback() {
				@Override
				public void call(List<JNode> nodes) {
					boolean done=true;
					for(JNode node:nodes){
						byte[] bytes=node.getData();
						if(bytes==null){
							bytes=executor.getPath(node.getPath());
						}
						InstanceNodeVal instanceNodeVal=JJSON.get().parse(new String(bytes, Charset.forName("utf-8")),
								InstanceNodeVal.class);
						if(!NodeStatus.COMPLETE.equals(instanceNodeVal.getStatus())){
							done=false;
						}
					}
					
					long instanceId=instance.getSequence();
					
					if(done){
						complete(_path);
						close(instanceId, _path, CacheType.CHILD);
					}
					
					Collections.sort(nodes, new Comparator<JNode>() {
						@Override
						public int compare(JNode o1, JNode o2) {
							return pathSequence(o1.getPath())-pathSequence(o2.getPath());
						}
					});
					
					
					Instance instance=workflowMaster.getInstances().get(instanceId);
					InstanceNode instanceNode=instance.getInstanceNodes().get(_path);
					if("0".equals(instanceNode.getNodeData().getParallel())){
						InstanceNodeVal latestNode=null;
						for(int i=nodes.size()-1;i>-1;i--){
							JNode tempNode=nodes.get(i);
							byte[] bytes=tempNode.getData();
							if(bytes==null){
								bytes=executor.getPath(tempNode.getPath());
							}
							InstanceNodeVal instanceNodeVal=JJSON.get().parse(
									new String(bytes, Charset.forName("utf-8")),
									InstanceNodeVal.class);
							if(!NodeStatus.COMPLETE.equals(instanceNodeVal.getStatus())){
								latestNode=instanceNodeVal;
							}
							else{
								break;
							}
						}
						if(latestNode!=null){
							NodeData nodeData=instanceNode.getNodeData();
							NodeData find=null;
							for(NodeData temp:nodeData.getNodes()){
								if(temp.getId()==latestNode.getId()){
									find=temp;
									break;
								}
							}
							propagateWorkerPath(latestNode, instanceNode, find,
									instance);
						}
					}
				}
				
			}, Executors.newFixedThreadPool(1, new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					return new Thread(r, workflowPath(instance.getWorkflow())+"{watch children}");
				}
			}),PathChildrenCacheEvent.Type.CHILD_UPDATED);
			instanceNode.setPathChildrenCache(cache);
			instance.addInstanceNode(_path, instanceNode);
		}
		
	}
	
	private int workerId(String path){
		return Integer.parseInt(path.substring(path.lastIndexOf("/")).split("-")[1]);
	}
	
	private synchronized void attachWorkersPathWatcher(final Workflow workflow){
		if(workflow.getPluginWorkersPathCache()!=null) return ;
		String _path=workflow.getPluginWorkersPath();
		final PathChildrenCache cache= executor.watchChildrenPath(_path, 
				new JZooKeeperConnecter.NodeChildrenCallback() {
			@Override
			public void call(List<JNode> nodes) {
				for(JNode node:nodes){
					String path=node.getPath();
					int workerId=workerId(path);
					if(!workflow.getWorkerPaths().containsKey(workerId)){
						workflow.addWorkerPath(workerId, path);
					}
				}
			}
		}, Executors.newFixedThreadPool(1, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, workflowPath(workflow)+"{watch works children}");
			}
		}),PathChildrenCacheEvent.Type.CHILD_ADDED,
				PathChildrenCacheEvent.Type.CHILD_REMOVED);
		workflow.setPluginWorkersPathCache(cache);
	}
	
	private synchronized void createMasterMeta(){
		System.out.println(logPrefix()+" got workflow leader ... "); 
		if(workflowMaster!=null) return;
		workflowMaster=new WorkflowMaster();
		attachWorfkowTriggerWatcher(null);
		attachWorfkowAddWatcher();
	}
	
	private Instance createInstance(Workflow workflow){
		
		Long sequence=getSequence();
		Instance instance=new Instance();
		instance.setWorkflow(workflow);
		instance.setSequence(sequence);
		
		createInstancePath(instance.getWorkflow().getNodeData(),instance);
		
		attachInstanceChildPathWatcher(instance);
		
		attachWorfkowTriggerWatcher(workflow);
		
		workflowMaster.addWorkflow(workflow);
		
		workflowMaster.addInstance(sequence, instance);
		
		return instance;
	}
	
	private void attachWorfkowTriggerWatcher(final Workflow workflow){
		
		if(workflow!=null){
			if(workflow.getWorkflowTriggerCache()!=null) return ;
		}
		
		
		final String path=workflowTrigger(workflow);
		if(!executor.exists(path)){
			executor.createPath(path);
		}
		NodeCache cache=executor.watchPath(path, new JZooKeeperConnecter.NodeCallback() {
			
			@Override
			public void call(JNode node) {
				WorkflowMeta workflowMeta=JJSON.get().parse(node.getStringData(), WorkflowMeta.class);
				Workflow workflow=workflowMaster.getWorkflow(workflowMeta.getName());
				if(workflow==null){
					throw new RuntimeException("workflow is missing, to add workflow in the container.");
				}
				Instance instance=createInstance(workflow);
				start(instance);
			}
		}, Executors.newFixedThreadPool(1, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, workflowPath(workflow)+"{watcher workflow trigger}");
			}
		}));
		if(workflow==null){
			workflowMaster.setWorkflowTriggerCache(cache);
		}else{
			workflow.setWorkflowTriggerCache(cache);
		}
	}
	
	private synchronized void attachWorfkowAddWatcher(){
		final String workflowAddPath=workflowAddPath();
		if(!executor.exists(workflowAddPath)){
			executor.createPath(workflowAddPath);
		}
		PathChildrenCache cache=  executor.watchChildrenPath(workflowAddPath, new JZooKeeperConnecter.NodeChildrenCallback() {
			
			@Override
			public void call(List<JNode> nodes) {
				for(JNode node:nodes){
					byte[] bytes=node.getData();
					if(bytes==null){
						bytes=executor.getPath(node.getPath());
					}
					WorkflowMeta workflowMeta=JJSON.get().parse(JStringUtils.utf8(bytes), WorkflowMeta.class);
					addWorkflow(workflowMeta);
				}
			}
		} , Executors.newFixedThreadPool(1, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, workflowAddPath+"{watcher workflow add}");
			}
		}), PathChildrenCacheEvent.Type.CHILD_ADDED
				,PathChildrenCacheEvent.Type.CHILD_REMOVED
				,PathChildrenCacheEvent.Type.CHILD_UPDATED);
		workflowMaster.setWorkfowAddCache(cache);
	}
	
	private void propagateWorkerPath(InstanceNodeVal triggerInstanceNodeVal,
			InstanceNode triggerInstanceNode,NodeData nodeData,Instance instance){
		if(nodeData.hasChildren()){
			if("1".equals(nodeData.getParallel())){
				for(NodeData thisNodeData:nodeData.getNodes()){
					propagateWorkerPath(triggerInstanceNodeVal, triggerInstanceNode,
							thisNodeData, instance);
				}
			}
			else{
				NodeData thisNodeData=nodeData.getNodes().get(0);
				propagateWorkerPath(triggerInstanceNodeVal, triggerInstanceNode,
						thisNodeData, instance);
			}
		}else{
			start(nodeData.getId(), instancePath(nodeData.getPath(), instance), instance);
		}
	}
	
	private void startLeader(){
		
		try {
			leaderLatch.start();
			leaderLatch.await();
			createMasterMeta();
			
			while(true){
				try{
					synchronized (this) {
						wait();
					}
				}catch (InterruptedException e) {
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(leaderLatch.hasLeadership()){
					leaderLatch.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	ZookeeperExecutor getExecutor() {
		return executor;
	}
	
	public Map getConf() {
		return conf;
	}
	
	private String logPrefix(){
		return logPrefix;
	}
}
