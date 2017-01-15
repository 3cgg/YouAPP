package j.jave.kernal.streaming.coordinator;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.google.common.collect.Maps;
import com.google.common.collect.Queues;

import j.jave.kernal.streaming.Util;
import j.jave.kernal.streaming.coordinator.rpc.leader.ExecutingWorker;
import me.bunny.kernel._c.model.JModel;
import me.bunny.kernel._c.utils.JDateUtils;

/**
 * the instance is created when a new workflow is started
 * @author JIAZJ
 *
 */
public class Instance implements JModel,Closeable{
	
	/**
	 * the instance id
	 */
	private long sequence;
	
	/**
	 * the workflow parameters/configuration
	 */
	private Map<String, Object> conf;
	
	/**
	 * how many times from the workflow beginning
	 */
	private long count=1;
	
	/**
	 * the roor path of 
	 */
	private String rootPath;
	
	/**
	 * the workflow meta data
	 */
	private Workflow workflow;
	
	/**
	 * all node path that is virtual , not related to a real worker.
	 */
	private List<String> childPathWatcherPaths=new ArrayList<>();
	
	/**
	 * the node path that is related to a real worker
	 */
	private List<String> workerPaths=new ArrayList<>();
	
	/**
	 * the node path , instance node mapping
	 */
	private Map<String, InstanceNode> instanceNodes=Maps.newConcurrentMap();
	
	/**
	 * KEY :  worker path
	 */
	private Map<String,String> errors=Maps.newHashMap();
	
	private WorkerMaster workerMaster=new WorkerMaster();
	
	private long heartBeatTime=-1;
	
	private Queue<ExecutingWorker> executingWorkers=Queues.newLinkedBlockingQueue(10);
	
	public void addChildPathWatcherPath(String path){
		childPathWatcherPaths.add(path);
	}
	
	public void addWorkerPath(String path){
		workerPaths.add(path);
	}
	
	public void addInstanceNode(String path, InstanceNode instanceNode){
		instanceNodes.put(path, instanceNode);
	}
	
	public void updateInstanceNodeVal(String path, InstanceNodeVal instanceNodeVal){
		getInstanceNode(path).setInstanceNodeVal(instanceNodeVal);
	}
	
	public InstanceNode getInstanceNode(String path){
		return instanceNodes.get(path);
	}
	
	@Override
	public void close() throws IOException {
		CloseException exception=new CloseException();
		for(InstanceNode instanceNode:instanceNodes.values()){
			try{
				instanceNode.close();
			}catch (Exception e) {
				exception.addMessage(e.getMessage());
			}
		}
		
		try{
			workerMaster.close();
		}catch (Exception e) {
			exception.addMessage(e.getMessage());
		}
		
		if(exception.has())
			throw exception;
	}
	
	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public List<String> getChildPathWatcherPaths() {
		return childPathWatcherPaths;
	}

	public void setChildPathWatcherPaths(List<String> childPathWatcherPaths) {
		this.childPathWatcherPaths = childPathWatcherPaths;
	}

	public List<String> getWorkerPaths() {
		return workerPaths;
	}

	public void setWorkerPaths(List<String> workerPaths) {
		this.workerPaths = workerPaths;
	}

	public Map<String, InstanceNode> getInstanceNodes() {
		return instanceNodes;
	}

	public void setInstanceNodes(Map<String, InstanceNode> instanceNodes) {
		this.instanceNodes = instanceNodes;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public Map<String, Object> getConf() {
		return conf;
	}

	public void setConf(Map<String, Object> conf) {
		this.conf = conf;
	}
	
	public void addError(String path,Throwable error){
		addError(path,Util.getMsg(error));
	}
	
	public void addError(String path,String error){
		if(!errors.containsKey(path)){
			errors.put(path, error);
		}
	}
	
	public Collection<String> errors() {
		return errors.values();
	}
	
	public String getErrorMessage() {
		StringBuffer buffer=new StringBuffer();
		for(String string:errors()){
			buffer.append(string+"\r\n----------------------------------\r\n");
		}
		return buffer.toString();
	}
	
	public WorkerMaster workerMaster() {
		return workerMaster;
	}
	
	@Deprecated
	public WorkerMaster getWorkerMaster() {
		return workerMaster;
	}

	public long getHeartBeatTime() {
		return heartBeatTime;
	}
	
	public void setHeartBeatTime(long heartBeatTime) {
		if(this.heartBeatTime<heartBeatTime){
			this.heartBeatTime = heartBeatTime;
		}
	}
	
	public String getHeartBeatTimeStr() {
		if(heartBeatTime<0) return "";
		return JDateUtils.formatWithMSeconds(new Date(heartBeatTime));
	}
	
	public synchronized boolean sendHeartbeats(ExecutingWorker executingWorker){
		setHeartBeatTime(executingWorker.getTime());
		boolean success=executingWorkers.offer(executingWorker);
		if(!success){
			executingWorkers.remove();
			executingWorkers.offer(executingWorker);
		}
		return true;
	}
	
	@Deprecated
	public Queue<ExecutingWorker> getExecutingWorkers() {
		return executingWorkers;
	}
	
	
}
