/**
 * 
 */
package j.jave.kernal.jave.support.detect;

/**
 *  how to load resources. 
 * @author J
 * @param <T> extends {@link JProvider}
 */
public interface JResourceDetect<T extends JProvider> {

	/**
	 * get all resource informations fist time, 
	 * the same one returned if call subsequently.
	 * @return {@link JProvider}
	 */
	public T detect();
	
	/**
	 * force refresh the resources. scan and wrap resources every time. 
	 * @return {@link JProvider}
	 */
	public T refresh();
	
}
