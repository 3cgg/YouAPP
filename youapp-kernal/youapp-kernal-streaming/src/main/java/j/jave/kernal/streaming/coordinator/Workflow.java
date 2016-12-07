package j.jave.kernal.streaming.coordinator;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;

import j.jave.kernal.jave.model.JModel;
import j.jave.kernal.jave.utils.JDateUtils;
import j.jave.kernal.streaming.coordinator.rpc.leader.ExecutingWorker;

@SuppressWarnings("serial")
public class Workflow implements JModel,Closeable{
	
	private String name;
	
	/**
	 * watcher on {@link #pluginWorkersPath}
	 */
	@JsonIgnore
	private transient PathChildrenCache pluginWorkersPathCache;
	
	private final String pluginWorkersPath;

	/**
	 * automatically initialized later, watcher children updated on {@link #pluginWorkersPath}
	 * <pre>KEY is worker id , VALUE is worker path.
	 */
	private Map<Integer,String> workerPaths=Maps.newConcurrentMap();
	
	/**
	 * the workflow definition information 
	 */
	private WorkflowMeta workflowMeta;
	
	private NodeData nodeData;
	
	/**
	 * how many times from the workflow beginning
	 */
	private long count;
	
	/**
	 * watcher on the special workflow
	 */
	@JsonIgnore
	private transient NodeCache workflowTriggerCache;
	
	/**
	 * what time the workflow is online , i.e. the status changes to the {@link WorkflowStatus#ONLINE}
	 */
	private long onlineStartTime=-1;
	
	private WorkflowStatus status=WorkflowStatus.OFFLINE;
	
	private WorkflowCheck workflowCheck=new WorkflowCheck();
	
//	private final Object sync=new Object();
	
	public Workflow(String name) {
		this(name,Maps.newConcurrentMap(),null);
	}
	
	public Workflow(String name,Map conf,NodeDataGenerator nodeDataGenerator) {
		if(name==null||name.isEmpty()){
			throw new RuntimeException("name is misssing.");
		}
		this.name=name;
		this.name = name;
		pluginWorkersPath=pluginWorkersPath();
		if(nodeDataGenerator!=null)
			this.nodeData=nodeDataGenerator.generate(name, conf);
	}
	
	class WorkflowCheck{
		
		/**
		 * which executing instance of the workflow
		 */
		private volatile Long lockSequence;
		
		boolean isOffline(){
			return status.isOffline();
		}
		
		boolean isOnline(){
			return status.isOnline();
		}
		
		boolean isError(){
			return status.isError();
		}
		
		/**
		 * check whether the workflow is running if any instance of the workflow is not completed.
		 * @return
		 */
		boolean isLock(){
			return lockSequence!=null;
		}
		
		/**
		 * attempt to express the workflow is ready for next start
		 * @param sequence
		 * @return
		 */
		synchronized boolean release(Long sequence){
			if(tryLock(sequence)){
				lockSequence=null;
				return true;
			}else{
				return false;
			}
		}
		
		/**
		 * attempt to lock the workflow
		 * @param sequence
		 * @return
		 */
		synchronized boolean tryLock(Long sequence){
			if(isLock()){
				if(_isLock0(sequence)){
					return true;
				}
				return false;
			}
			lockSequence=sequence;
			return true;
		}

		private boolean _isLock0(Long sequence) {
			return lockSequence.compareTo(sequence)==0;
		}
		
		public Long getLockSequence() {
			return lockSequence;
		}
		
	}
	
	@Override
	public void close() throws IOException {
		CloseException exception=new CloseException();
		if(pluginWorkersPathCache!=null){
			try {
				pluginWorkersPathCache.close();
			} catch (IOException e) {
				exception.addMessage(e.getMessage());
			}
		}
		if(workflowTriggerCache!=null){
			try{
				workflowTriggerCache.close();
			}catch (Exception e) {
				exception.addMessage(e.getMessage());
			}
		}
		if(exception.has())
			throw exception;
	}
	
	String pluginWorkersPath(){
		return CoordinatorPaths.BASE_PATH+"/pluginWorkers/"+name+"-workers";
	}

	public String getName() {
		return name;
	}

	public void addWorkerPath(Integer workerId,String path){
		workerPaths.put(workerId, path);
	}
	
	public Map<Integer, String> getWorkerPaths() {
		return Collections.unmodifiableMap(workerPaths);
	}

	public NodeData getNodeData() {
		return nodeData;
	}
	
	@Deprecated
	public String getPluginWorkersPath() {
		return pluginWorkersPath;
	}
	
	public void setNodeData(NodeData nodeData) {
		this.nodeData = nodeData;
	}
	
	public void setWorkflowTriggerCache(NodeCache workflowTriggerCache) {
		this.workflowTriggerCache = workflowTriggerCache;
	}
	
	public NodeCache getWorkflowTriggerCache() {
		return workflowTriggerCache;
	}
	
	public PathChildrenCache getPluginWorkersPathCache() {
		return pluginWorkersPathCache;
	}

	public void setPluginWorkersPathCache(PathChildrenCache pluginWorkersPathCache) {
		this.pluginWorkersPathCache = pluginWorkersPathCache;
	}

	public WorkflowMeta getWorkflowMeta() {
		return workflowMeta;
	}

	public void setWorkflowMeta(WorkflowMeta workflowMeta) {
		this.workflowMeta = workflowMeta;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
	
	public boolean containsWorker(int workerId){
		return nodeData.containsWorker(workerId);
	}
	
	public WorkflowCheck workflowCheck() {
		return workflowCheck;
	}
	
	@Deprecated
	public WorkflowCheck getWorkflowCheck() {
		return workflowCheck;
	}
	
	public String getOnlineStartTimeStr() {
		if(onlineStartTime<0) return "";
		return JDateUtils.formatWithMSeconds(new Date(onlineStartTime));
	}
	
	public long getOnlineStartTime() {
		return onlineStartTime;
	}
	
	synchronized void setOnlineStartTime(long onlineStartTime) {
		if(this.onlineStartTime<onlineStartTime){
			this.onlineStartTime=onlineStartTime;
		}
	}
	
	boolean sendHeartbeats(ExecutingWorker executingWorker){
		if(workflowCheck.isOffline()){
			setOnlineStartTime(executingWorker.getTime());
		}
		return true;
	}

	synchronized void setOnline() {
		this.status = WorkflowStatus.ONLINE;
		setOnlineStartTime(new Date().getTime());
	}
}
