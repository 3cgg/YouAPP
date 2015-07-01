/**
 * 
 */
package j.jave.framework.commons.filedistribute;

/**
 * @author J
 */
public class JLocalFileDistServiceConfiguration implements  JLocalFileDistServiceConfigure{
	
	private JLocalFilePathStrategy localFilePathStrategy;
	
	private int fixedThreadCount;

	public JLocalFilePathStrategy getLocalFilePathStrategy() {
		return localFilePathStrategy;
	}

	public void setLocalFilePathStrategy(
			JLocalFilePathStrategy localFilePathStrategy) {
		this.localFilePathStrategy = localFilePathStrategy;
	}

	public int getFixedThreadCount() {
		return fixedThreadCount;
	}

	public void setFixedThreadCount(int fixedThreadCount) {
		this.fixedThreadCount = fixedThreadCount;
	}
	
}

