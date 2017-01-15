package j.jave.kernal.streaming.coordinator;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

import org.apache.curator.framework.recipes.cache.PathChildrenCache;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;

import me.bunny.kernel._c.model.JModel;

public class WorkerMaster implements JModel ,Closeable{

	/**
	 * KEY : executing worker path / include instance id
	 */
	@JsonIgnore
	private transient Map<String, PathChildrenCache> processorsWathers=Maps.newHashMap();
	
	private InstaneCheck instaneCheck=new InstaneCheck();
	
	class InstaneCheck{
		
		private Map<String, NodeStatus> status=Maps.newHashMap();
		
		public Map<String, NodeStatus> getStatus() {
			return status;
		}
		
		boolean isComplete(String path,NodeStatus nodeStatus){
			isStart(path);
			NodeStatus _nodeStatus=get(path);
			if(_nodeStatus.isComplete()){
				return true;
			}
			else{
				status.put(path, nodeStatus);
				return false;
			}
		}
		
		boolean isStart(String path){
			NodeStatus nodeStatus=get(path);
			if(nodeStatus==null){
				status.put(path, NodeStatus.PROCESSING);
				return false;
			}
			return true;
		}

		private NodeStatus get(String path) {
			return status.get(path);
		}
		
	}

	@Override
	public void close() throws IOException {
		CloseException exception=new CloseException();
		if(!processorsWathers.isEmpty()){
			for(PathChildrenCache cache:processorsWathers.values())
			try {
				cache.close();
			} catch (IOException e) {
				exception.addMessage(e.getMessage());
			}
		}
		if(exception.has())
			throw exception;
	}

	public void addProcessorsWather(String workerExecutingInstancePath,PathChildrenCache processorsWather) {
		if(processorsWathers.containsKey(workerExecutingInstancePath)){
			throw new IllegalStateException("must close the previous : "+workerExecutingInstancePath);
		}
		processorsWathers.put(workerExecutingInstancePath, processorsWather);
	}
	
	public void closeInstance(String workerExecutingInstancePath) throws Exception{
		PathChildrenCache cache=processorsWathers.get(workerExecutingInstancePath);
		if(cache!=null){
			cache.close();
		}
	}

	public InstaneCheck instaneCheck() {
		return instaneCheck;
	}

	void setInstaneCheck(InstaneCheck instaneCheck) {
		this.instaneCheck = instaneCheck;
	}
	
	public InstaneCheck getInstaneCheck() {
		return instaneCheck;
	}
	
}
