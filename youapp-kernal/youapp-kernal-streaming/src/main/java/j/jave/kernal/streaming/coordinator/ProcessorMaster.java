package j.jave.kernal.streaming.coordinator;

import java.io.Closeable;
import java.io.IOException;

import org.apache.curator.framework.recipes.cache.PathChildrenCache;

import j.jave.kernal.jave.model.JModel;

public class ProcessorMaster implements JModel ,Closeable{

	private PathChildrenCache processorsWather;
	
	@Override
	public void close() throws IOException {
		CloseException exception=new CloseException();
		if(processorsWather!=null){
			try {
				processorsWather.close();
			} catch (IOException e) {
				exception.addMessage(e.getMessage());
			}
		}
		if(exception.has())
			throw exception;
	}

	public void setProcessorsWather(PathChildrenCache processorsWather) {
		this.processorsWather = processorsWather;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
