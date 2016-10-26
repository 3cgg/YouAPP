package j.jave.kernal.streaming.coordinator;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import com.google.common.collect.Maps;

import j.jave.kernal.jave.model.JModel;
import j.jave.kernal.streaming.coordinator.NodeData.NodeDataGenerator;

@SuppressWarnings("serial")
public class Workflow implements JModel,Closeable{
	
	private String name;
	
	private String pluginWorkersPath;

	/**
	 * automatically initialized later, watcher children updated on {@link #pluginWorkersPath}
	 * <pre>KEY is worker id , VALUE is worker path.
	 */
	private Map<Integer,String> workerPaths=Maps.newConcurrentMap();
	
	private NodeData nodeData;
	
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
	
	void setNodeData(NodeData nodeData) {
		this.nodeData = nodeData;
	}
	
}
