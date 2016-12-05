package j.jave.kernal.streaming.coordinator;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

import org.apache.curator.framework.recipes.cache.PathChildrenCache;

import com.google.common.collect.Maps;

import j.jave.kernal.jave.model.JModel;

public class WorkerMaster implements JModel ,Closeable{

	/**
	 * KEY : executing worker path / include instance id
	 */
	private Map<String, PathChildrenCache> processorsWathers=Maps.newHashMap();
	
	private InstaneCheck instaneCheck=new InstaneCheck();
	
	class InstaneCheck{
		
		private Map<Long, WorkerPathVal> instances=Maps.newHashMap();
		
		boolean isDone(WorkerPathVal workerPathVal){
			boolean contains=instances.containsKey(workerPathVal.getSequence());
			if(!contains){
				instances.put(workerPathVal.getSequence(), workerPathVal);
			}
			return contains;
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
		instaneCheck=null;
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

	public void setInstaneCheck(InstaneCheck instaneCheck) {
		this.instaneCheck = instaneCheck;
	}
	
	
	
}
