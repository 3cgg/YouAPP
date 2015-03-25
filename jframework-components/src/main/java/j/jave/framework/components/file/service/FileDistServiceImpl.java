/**
 * 
 */
package j.jave.framework.components.file.service;

import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.file.model.FileDist;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service(value="fileDistServiceImpl")
public class FileDistServiceImpl implements FileDistService {
	
	private static final String FILELOCATION="D:/java_/server-directory/file";
	
	private static final Logger LOGGER=LoggerFactory.getLogger(FileDistServiceImpl.class);
	
	private static final ThreadPoolExecutor EXECUTOR=new ScheduledThreadPoolExecutor(15);
	
	static class SFile extends Thread{
		
		private File file;
		
		private byte[] bytes;
		
		public SFile(File file ,byte[] bytes) {
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
	public URL distribute(FileDist fileDist) throws ServiceException {
		try {
			String fileName = fileDist.getFileName();
			if (fileName.indexOf("/") != -1) {
				fileName = fileName.substring(fileName.lastIndexOf("/"));
			}
			fileName = new Date().getTime() + "-" + fileName;
			File file = new File(FILELOCATION + "/" + fileName);
			EXECUTOR.execute(new SFile(file, fileDist.getBytes()));
			return file.toURI().toURL();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ServiceException(e);
		}
	}

}
