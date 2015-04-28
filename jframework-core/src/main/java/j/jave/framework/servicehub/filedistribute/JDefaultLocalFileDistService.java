/**
 * 
 */
package j.jave.framework.servicehub.filedistribute;

import j.jave.framework.io.JFile;
import j.jave.framework.servicehub.JServiceException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * thread safety . 
 * @author J
 */
public class JDefaultLocalFileDistService implements JFileDisService, JDefaultLocalFileDistServiceConfigure{
	
	private static final Logger LOGGER=LoggerFactory.getLogger(JDefaultLocalFileDistService.class);
	
	private static final ThreadPoolExecutor EXECUTOR=new ScheduledThreadPoolExecutor(15);
	
	protected JDefaultLocalFilePathStrategy defaultLocalFilePathStrategy;
	
	protected String localDirectory;
	
	static class WriteFile extends Thread{
		
		private File file;
		
		private byte[] bytes;
		
		public WriteFile(File file ,byte[] bytes) {
			this.file=file;
			this.bytes=bytes;
		}
		
		@Override
		public void run() {
			FileOutputStream fileOutputStream =null;
			try{
				fileOutputStream = new FileOutputStream(file);
				fileOutputStream.write(bytes);
			}catch(Exception e){
				LOGGER.error(e.getMessage(), e);
			}finally{
				if(fileOutputStream!=null){
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						LOGGER.error(e.getMessage(), e);
					}
				}
			}
			
		}
	}
	
	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.file.service.FileDistService#distribute(j.jave.framework.components.file.model.FileDist)
	 */
	@Override
	public URI distribute(JFile file) throws JServiceException{
		try {
			
			if(defaultLocalFilePathStrategy==null){
				defaultLocalFilePathStrategy=new JDefaultLocalFilePathStrategy();
			}

			JHierarchicalPath hierarchicalPath= new JHierarchicalPath(file);
			hierarchicalPath.setRoot(localDirectory);
			URI uri=defaultLocalFilePathStrategy.resolveURI(hierarchicalPath);
			EXECUTOR.execute(new WriteFile(new File(uri), file.getFileContent()));

			return uri;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new JServiceException(e);
		}
	}

	
	/* (non-Javadoc)
	 * @see j.jave.framework.filedistribute.JDefaultLocalFileDistServiceConfigure#setJDefaultLocalFilePathStrategy(j.jave.framework.filedistribute.JDefaultLocalFilePathStrategy)
	 */
	@Override
	public void setJDefaultLocalFilePathStrategy(
			JDefaultLocalFilePathStrategy defaultLocalFilePathStrategy) {	
		this.defaultLocalFilePathStrategy=defaultLocalFilePathStrategy;
	}
	
	/* (non-Javadoc)
	 * @see j.jave.framework.filedistribute.JDefaultLocalFileDistServiceConfigure#setLocalDirectory(java.lang.String)
	 */
	@Override
	public void setLocalDirectory(String localDirectory) {
		this.localDirectory=localDirectory;
	}
}
