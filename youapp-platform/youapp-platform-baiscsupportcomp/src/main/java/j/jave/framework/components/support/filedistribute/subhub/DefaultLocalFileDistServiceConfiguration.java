/**
 * 
 */
package j.jave.framework.components.support.filedistribute.subhub;

import j.jave.framework.commons.filedistribute.JDefaultLocalFilePathStrategy;
import j.jave.framework.commons.filedistribute.JLocalFileDistServiceConfigure;
import j.jave.framework.commons.filedistribute.JLocalFilePathStrategy;

/**
 * @author J
 */
public class DefaultLocalFileDistServiceConfiguration implements
		JLocalFileDistServiceConfigure {

	private String localDirectory="D:/java_/server-directory/file" ;

	private int fixedThreadCount;
	
	private  JLocalFilePathStrategy localFilePathStrategy=new JDefaultLocalFilePathStrategy(localDirectory);
	
	public void setLocalDirectory(String localDirectory) {
		this.localDirectory=localDirectory;
	}
	
	public String getLocalDirectory() {
		return localDirectory;
	}

	@Override
	public void setLocalFilePathStrategy(
			JLocalFilePathStrategy localFilePathStrategy) {	
		this.localFilePathStrategy=localFilePathStrategy;
	}
	@Override
	public JLocalFilePathStrategy getLocalFilePathStrategy() {
		return localFilePathStrategy;
	}

	@Override
	public void setFixedThreadCount(int fixedThreadCount) {
		this.fixedThreadCount=fixedThreadCount;
	}

	@Override
	public int getFixedThreadCount() {
		return this.fixedThreadCount;
	}


	

	
}
