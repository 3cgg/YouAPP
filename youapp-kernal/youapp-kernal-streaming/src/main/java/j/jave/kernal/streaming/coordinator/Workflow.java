package j.jave.kernal.streaming.coordinator;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.recipes.cache.PathChildrenCache;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;

import j.jave.kernal.streaming.coordinator.rpc.leader.ExecutingWorker;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.model.JModel;
import me.bunny.kernel._c.utils.JDateUtils;

@SuppressWarnings("serial")
public class Workflow implements JModel,Closeable{
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(Workflow.class);
	
	
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
	
//	/**
//	 * watcher on the special workflow
//	 */
//	@JsonIgnore
//	private transient NodeCache workflowTriggerCache;
	
	/**
	 * what time the workflow is online , i.e. the status changes to the {@link WorkflowStatus#ONLINE}
	 */
	private long onlineStartTime=-1;
	
	private WorkflowStatus status=WorkflowStatus.OFFLINE;
	
	private final Object sync=new Object();
	
	private WorkflowCheck workflowCheck=new WorkflowCheck();
	
	private static ScheduledExecutorService workflowStatusExecutorService=_workflowStatusExecutorService();
	
	private static ScheduledExecutorService _workflowStatusExecutorService() {
		return 
				Executors.newScheduledThreadPool(1,new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						return new Thread(r,"workflow-status-check");
					}
				});
	}
	
	
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
		 * release current instance,means the workflow is ready for next start
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
//		if(workflowTriggerCache!=null){
//			try{
//				workflowTriggerCache.close();
//			}catch (Exception e) {
//				exception.addMessage(e.getMessage());
//			}
//		}
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
		return workerPaths;
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
	
//	public void setWorkflowTriggerCache(NodeCache workflowTriggerCache) {
//		this.workflowTriggerCache = workflowTriggerCache;
//	}
//	
//	public NodeCache getWorkflowTriggerCache() {
//		return workflowTriggerCache;
//	}
	
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
	
	synchronized void setStop() {
		this.status = WorkflowStatus.STOP;
	}
	
	synchronized void setError(Throwable t) {
		setError(WorkflowErrorCode.E0001.setThrowable(t));
	}
	
	synchronized boolean containsError(WorkflowErrorCode errorCode){
		return this.status.containsError(errorCode);
	}
	
	synchronized void setError(WorkflowErrorCode errorCode){
		if(this.status.isError()){
			this.status.setErrorCode(errorCode);
		}else{
			this.status = WorkflowStatus.ERROR.setErrorCode(errorCode);
		}
	}
	
	synchronized void removeError(WorkflowErrorCode errorCode,SimpleCallBack callBack){
		if(this.status.isError()){
			this.status.removeError(errorCode);
			notifyWorkflowRecoverIf(callBack);
		}
	}
	
	/**
	 * 
	 */
	private void notifyWorkflowRecoverIf(final SimpleCallBack callBack){
		if(workflowStatusExecutorService.isShutdown()){
			workflowStatusExecutorService=_workflowStatusExecutorService();
		}
		workflowStatusExecutorService.schedule(new Runnable() {
			@Override
			public void run() {
				synchronized (sync) {
					LOGGER.info("attempt to recover workflow :"+getName());
					WorkflowStatus status=Workflow.this.status;
					if(status.isError()
							&&status.recoverIf()){
						LOGGER.info("recover workflow :"+getName());
						setOnline();
						callBack.call(null);
					}
				}
			}
		}, 10000, TimeUnit.MILLISECONDS);
	}
	
	public WorkflowStatus getStatus() {
		return status;
	}
	
	/**
	 * only for JSON VIEW
	 * @return
	 */
	@Deprecated
	public List<Throwable> getErrors(){
		if(status.isError()){
			return status.getCause();
		}
		return Collections.EMPTY_LIST;
	}
	
}
