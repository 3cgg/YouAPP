package j.jave.kernal.streaming.coordinator;

import java.io.Closeable;
import java.io.IOException;

import org.apache.curator.framework.recipes.cache.PathChildrenCache;

import com.fasterxml.jackson.annotation.JsonIgnore;

import me.bunny.kernel.jave.model.JModel;

public class InstanceNode implements JModel,Closeable{

	/**
	 * worker id
	 */
	private int id;
	
	/**
	 * the instance id
	 */
	private long sequence;
	
	/**
	 * the wather on the {@link #path}
	 */
	@JsonIgnore
	private transient PathChildrenCache pathChildrenCache;
	
	/**
	 * the zookeeper path
	 */
	private String path;
	
	/**
	 * the special node of a workflow
	 */
	private NodeData nodeData;
	
	/**
	 * the zookeeper node value
	 */
	private InstanceNodeVal instanceNodeVal;

	@Override
	public void close() throws IOException {
		if(pathChildrenCache!=null){
			pathChildrenCache.close();
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public PathChildrenCache getPathChildrenCache() {
		return pathChildrenCache;
	}

	public void setPathChildrenCache(PathChildrenCache pathChildrenCache) {
		this.pathChildrenCache = pathChildrenCache;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public NodeData getNodeData() {
		return nodeData;
	}

	public void setNodeData(NodeData nodeData) {
		this.nodeData = nodeData;
	}

	public InstanceNodeVal getInstanceNodeVal() {
		return instanceNodeVal;
	}

	public void setInstanceNodeVal(InstanceNodeVal instanceNodeVal) {
		this.instanceNodeVal = instanceNodeVal;
	}

}
