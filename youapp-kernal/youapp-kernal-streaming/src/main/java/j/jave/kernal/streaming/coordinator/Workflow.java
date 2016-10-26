package j.jave.kernal.streaming.coordinator;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;

import com.google.common.collect.Maps;

import j.jave.kernal.jave.model.JModel;
import j.jave.kernal.streaming.coordinator.NodeData.NodeDataGenerator;

@SuppressWarnings("serial")
public class Workflow implements JModel,Closeable{
	
	private String name;
	
	/**
	 * watcher on {@link #pluginWorkersPath}
	 */
	private PathChildrenCache pluginWorkersPathCache;
	
	private String pluginWorkersPath;

	/**
	 * automatically initialized later, watcher children updated on {@link #pluginWorkersPath}
	 * <pre>KEY is worker id , VALUE is worker path.
	 */
	private Map<Integer,String> workerPaths=Maps.newConcurrentMap();
	
	private NodeData nodeData;
	
	/**
	 * watcher on the special workflow
	 */
	private NodeCache workflowTriggerCache;
	
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
	
	private String pluginWorkersPath(){
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
}
