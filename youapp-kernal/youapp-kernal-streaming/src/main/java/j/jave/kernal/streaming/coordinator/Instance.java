package j.jave.kernal.streaming.coordinator;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import j.jave.kernal.jave.model.JModel;

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

	public void addChildPathWatcherPath(String path){
		childPathWatcherPaths.add(path);
	}
	
	public void addWorkerPath(String path){
		workerPaths.add(path);
	}
	
	public void addInstanceNode(String path, InstanceNode instanceNode){
		instanceNodes.put(path, instanceNode);
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

}
