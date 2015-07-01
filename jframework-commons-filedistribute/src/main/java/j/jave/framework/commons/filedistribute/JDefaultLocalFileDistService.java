/**
 * 
 */
package j.jave.framework.commons.filedistribute;

import j.jave.framework.commons.eventdriven.exception.JServiceException;
import j.jave.framework.commons.eventdriven.servicehub.JEventExecutionException;
import j.jave.framework.commons.filedistribute.eventdriven.JFileDistStoreEvent;
import j.jave.framework.commons.filedistribute.eventdriven.JFileDistStoreListener;
import j.jave.framework.commons.io.JFile;
import j.jave.framework.commons.logging.JLogger;
import j.jave.framework.commons.logging.JLoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * the service distribute all incoming files via the custom strategy which may store the file in the remote or in the local.
 * a default named <code>JDefaultLocalFilePathStrategy</code> used, if custom strategy is not defined. the utilization
 * setup around ten threads to do with files request. see JDefaultLocalFileDistServiceConfigure for more configs
 * <p><strong>Note that the instance is always thread safety.</strong> 
 * @author J
 * @see JDefaultLocalFilePathStrategy
 * @see JLocalFileDistServiceConfigure
 * @see JHierarchicalPath
 */
public class JDefaultLocalFileDistService implements JFileDistService, JFileDistStoreListener{
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(JDefaultLocalFileDistService.class);
	
	private static int DEFAULT_FIXED_THREAD_COUNT=10;
	
	private int fixedThreadCount=DEFAULT_FIXED_THREAD_COUNT;
	
	private ExecutorService EXECUTOR=null;
	
	private JLocalFilePathStrategy localFilePathStrategy;
	
	static class WriteFile implements Runnable{
		
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
				fileOutputStream.flush();
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
	
	@Override
	public URI distribute(JFile file) throws JServiceException{
		try {
			URI uri=localFilePathStrategy.resolveURI(file);
			EXECUTOR.execute(new WriteFile(new File(uri), file.getFileContent()));
			return uri;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new JServiceException(e);
		}
	}
	
	@Override
	public Object trigger(JFileDistStoreEvent event) {
		try {
			return distribute(event.getFile());
		} catch (JServiceException e) {
			throw new JEventExecutionException(e);
		}
	}
	
	
	private JDefaultLocalFileDistService(){ }
	
	private static JDefaultLocalFileDistService defaultLocalFileDistService;
	
	/**
	 * always return a single instance for caller.
	 * <code> JDefaultLocalFilePathStrategy <code> uses in the service,
	 * with a fixed thread count of {@link #DEFAULT_FIXED_THREAD_COUNT}
	 * @param localRootDirectory
	 * @see JDefaultLocalFilePathStrategy
	 * @return
	 */
	public static JDefaultLocalFileDistService newSingleLocalFileDistService(String localRootDirectory){
		if(defaultLocalFileDistService==null){
			synchronized (JDefaultLocalFileDistService.class) {
				JDefaultLocalFileDistService defaultLocalFileDistService=new JDefaultLocalFileDistService();
				JLocalFileDistServiceConfiguration localFileDistServiceConfiguration=new JLocalFileDistServiceConfiguration();
				localFileDistServiceConfiguration.setFixedThreadCount(10);
				localFileDistServiceConfiguration.setLocalFilePathStrategy(new JDefaultLocalFilePathStrategy(localRootDirectory));
				init(defaultLocalFileDistService,localFileDistServiceConfiguration);
				JDefaultLocalFileDistService.defaultLocalFileDistService=defaultLocalFileDistService;
			}
		}
		return defaultLocalFileDistService;
	}

	/**
	 * always return new instance each of time.
	 * <strong>note that more thread counts will low the performance of the system.</strong>
	 * recommend you hold the instance returned in the global scope of system.
	 * @param defaultLocalFileDistServiceConfigure
	 * @return
	 */
	public static JDefaultLocalFileDistService newLocalFileDistService(JLocalFileDistServiceConfigure localFileDistServiceConfigure){
		JDefaultLocalFileDistService defaultLocalFileDistService=new JDefaultLocalFileDistService();
		init(defaultLocalFileDistService,localFileDistServiceConfigure);
		return defaultLocalFileDistService;
	}
	
	private static void init(
			JDefaultLocalFileDistService defaultLocalFileDistService,
			JLocalFileDistServiceConfigure localFileDistServiceConfigure) {
		int fixedThreadCount=localFileDistServiceConfigure.getFixedThreadCount();
		if(fixedThreadCount>0){
			defaultLocalFileDistService.fixedThreadCount=fixedThreadCount;
		}
		JLocalFilePathStrategy localFilePathStrategy=localFileDistServiceConfigure.getLocalFilePathStrategy();
		if(localFilePathStrategy!=null){
			defaultLocalFileDistService.localFilePathStrategy=localFilePathStrategy;
		}
		defaultLocalFileDistService.EXECUTOR=Executors.newFixedThreadPool(defaultLocalFileDistService.fixedThreadCount);
	}
	
	
	
}
