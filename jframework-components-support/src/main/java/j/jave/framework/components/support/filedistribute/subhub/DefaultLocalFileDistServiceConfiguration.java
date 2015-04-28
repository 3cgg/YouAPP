/**
 * 
 */
package j.jave.framework.components.support.filedistribute.subhub;

import j.jave.framework.servicehub.filedistribute.JDefaultLocalFileDistServiceConfigure;
import j.jave.framework.servicehub.filedistribute.JDefaultLocalFilePathStrategy;

/**
 * @author J
 */
public class DefaultLocalFileDistServiceConfiguration implements
		JDefaultLocalFileDistServiceConfigure {

	private String localDirectory="D:/java_/server-directory/file" ;

	private  JDefaultLocalFilePathStrategy jDefaultLocalFilePathStrategy=new JDefaultLocalFilePathStrategy();
	
	/* (non-Javadoc)
	 * @see j.jave.framework.filedistribute.JDefaultLocalFileDistServiceConfigure#setLocalDirectory(java.lang.String)
	 */
	@Override
	public void setLocalDirectory(String localDirectory) {
		this.localDirectory=localDirectory;
	}

	/* (non-Javadoc)
	 * @see j.jave.framework.filedistribute.JDefaultLocalFileDistServiceConfigure#setJDefaultLocalFilePathStrategy(j.jave.framework.filedistribute.JDefaultLocalFilePathStrategy)
	 */
	@Override
	public void setJDefaultLocalFilePathStrategy(
			JDefaultLocalFilePathStrategy defaultLocalFilePathStrategy) {
		this.jDefaultLocalFilePathStrategy=defaultLocalFilePathStrategy;
	}

	public String getLocalDirectory() {
		return localDirectory;
	}

	public JDefaultLocalFilePathStrategy getDefaultLocalFilePathStrategy() {
		return jDefaultLocalFilePathStrategy;
	}

	
}
