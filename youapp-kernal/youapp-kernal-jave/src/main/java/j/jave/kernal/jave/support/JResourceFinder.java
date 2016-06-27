/**
 * 
 */
package j.jave.kernal.jave.support;


/**
 *  how to load resources. 
 * @author J
 * @param <T> extends {@link JProvider}
 */
public interface JResourceFinder<T extends JProvider> extends JFinder<T> {
	
	/**
	 * return cache
	 * @return {@link JProvider}
	 */
	public T cache();
	
	/**
	 * force refresh the resources. scan and wrap resources every time. 
	 * @return {@link JProvider}
	 */
	public T refresh();
	
}
