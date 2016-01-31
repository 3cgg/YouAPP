/**
 * 
 */
package j.jave.framework.commons.filedistribute;

/**
 *  * the configuration defined class.
 * <p> JDefaultLocalFilePathStrategy is mandatory, if local root directory is empty.
 * @author J
 */
public interface JLocalFileDistServiceConfigure {
	
	public void setLocalFilePathStrategy(JLocalFilePathStrategy localFilePathStrategy);
	
	public JLocalFilePathStrategy getLocalFilePathStrategy( );
	
	public void setFixedThreadCount(int fixedThreadCount);
	
	public int getFixedThreadCount( );
}
