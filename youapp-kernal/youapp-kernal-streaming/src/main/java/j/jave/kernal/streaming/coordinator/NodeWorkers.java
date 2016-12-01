package j.jave.kernal.streaming.coordinator;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.Maps;

import j.jave.kernal.streaming.zookeeper.ZooKeeperConnector.ZookeeperExecutor;

@SuppressWarnings({"serial","rawtypes"})
public class NodeWorkers implements Serializable {

	private static ConcurrentMap<Integer, NodeWorker> map=Maps.newConcurrentMap();
	
	private static ZookeeperExecutor executor;
	
	public synchronized static void startup(ZookeeperExecutor executor){
		if(NodeWorkers.executor==null){
			NodeWorkers.executor=executor;
			SingleMonitor.startup(executor);
		}
		
	}
	
	private static void validate(){
	}
	
	
	public static synchronized NodeWorker get(int id,String name,String workflowName,Map conf){
		WorkflowMeta workflowMeta=new WorkflowMeta();
		workflowMeta.setName(workflowName);
		return get(id, workflowName, workflowMeta, conf);
	}
	
	public static synchronized NodeWorker get(int id,String name,WorkflowMeta workflowMeta,Map conf){
		validate();
		NodeWorker nodeWorker=  map.get(id);
		if(nodeWorker==null){
			nodeWorker=new NodeWorker(id, name, workflowMeta,executor,conf);
			map.put(id, nodeWorker);
		}
		return nodeWorker;
	}
	
	public static synchronized NodeWorker get(int id){
		NodeWorker nodeWorker=  map.get(id);
		if(nodeWorker==null){
			throw new IllegalStateException("node worker is missing : "+id);
		}
		return nodeWorker;
	}
	
}
