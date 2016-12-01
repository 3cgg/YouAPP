package j.jave.kernal.streaming.coordinator;

import java.util.ArrayList;
import java.util.List;

import j.jave.kernal.jave.model.JModel;

public class NodeData implements JModel,IParallel{
	
	/**
	 * the worker id , that may be a virtual /  real worker 
	 */
	private int id;
	
	/**
	 * the node name , as a part of path
	 */
	private String name;
	
	/**
	 * 1 is parrallel, otherwise 0
	 */
	private String parallel;
	
	/**
	 * the node path
	 */
	private String path;
	
	/**
	 * the child nodes
	 */
	private List<NodeData> nodes=new ArrayList<>();
	
	public boolean hasChildren(){
		return !nodes.isEmpty();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.path="/"+name;
	}

	public String getPath() {
		return path;
	}

	public List<NodeData> getNodes() {
		return nodes;
	}

	public void setNodes(List<NodeData> nodes) {
		this.nodes = nodes;
	}

	public String getParallel() {
		return parallel;
	}

	public void setParallel(String parallel) {
		this.parallel = parallel;
	}
	
	public void addParent(NodeData parent){
		this.path=parent.getPath()+"/"+name;
		parent.nodes.add(this);
	}
	
	public boolean isParallel(){
		return IParallel._TRUE.equals(parallel);
	}

	public boolean containsWorker(int worker){
		boolean contains=id==worker;
		if(contains){
			return true;
		}
		for(NodeData nodeData:nodes){
			contains=nodeData.containsWorker(worker);
			if(contains){
				return true;
			}
		}
		return false;
	}
	
}
