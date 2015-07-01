/**
 * 
 */
package j.jave.framework.components.support.filedistribute.subhub;

import j.jave.framework.commons.filedistribute.JDefaultLocalFileDistService;
import j.jave.framework.commons.filedistribute.JDefaultLocalFilePathStrategy;
import j.jave.framework.commons.filedistribute.JLocalFilePathStrategy;
import j.jave.framework.commons.utils.JStringUtils;
import j.jave.framework.components.core.servicehub.SpringServiceFactorySupport;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="fileDisServiceFactory")
public class FileDistServiceFactory extends SpringServiceFactorySupport<FileDistService> {
	
	public FileDistServiceFactory() {
		super(FileDistService.class);
	}
	
	@Autowired(required=false)
	private DefaultLocalFileDistServiceConfiguration localFileDistServiceConfig;
	
	private FileDistService fileDisService;
	
	private Object sync=new Object();
	
	@Override
	public FileDistService getService() {
		
		if(fileDisService==null){
			synchronized (sync) {
				DefaultLocalFileDistServiceImpl defaultLocalFileDistServiceImpl=  (DefaultLocalFileDistServiceImpl) getBeanByName("defaultLocalFileDistServiceImpl"); 
				
				if(localFileDistServiceConfig==null){
					localFileDistServiceConfig=new DefaultLocalFileDistServiceConfiguration();
					LOGGER.info("use default local fle distribute configuration."); 
				}
				
				JLocalFilePathStrategy localFilePathStrategy=localFileDistServiceConfig.getLocalFilePathStrategy();
				if(localFilePathStrategy==null){
					String localDirectory=localFileDistServiceConfig.getLocalDirectory();
					if(JStringUtils.isNotNullOrEmpty(localDirectory)){
						File file=new File(localDirectory);
						if(!file.exists()){
							throw new IllegalArgumentException("directory of "+localDirectory+"does not exist.");
						}
						localFilePathStrategy=new JDefaultLocalFilePathStrategy(localDirectory);
					}
					else{
						throw new IllegalArgumentException("please define a local directory while strategy is not defined.");
					}
				}
				
				JDefaultLocalFileDistService defaultLocalFileDistService=JDefaultLocalFileDistService .newLocalFileDistService(localFileDistServiceConfig);
				defaultLocalFileDistServiceImpl.setDefaultLocalFileDistService(defaultLocalFileDistService);
				this.fileDisService= defaultLocalFileDistServiceImpl;
			}
		}
		return fileDisService;
		
	}
	
	@Override
	public String getName() {
		return "File Distribute Provider";
	}
}
