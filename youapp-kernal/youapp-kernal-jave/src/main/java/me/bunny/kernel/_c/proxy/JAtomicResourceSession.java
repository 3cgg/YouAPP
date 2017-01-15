package me.bunny.kernel._c.proxy;

/**
 * to restrict operating atomically the resource 
 * @author J
 *
 */
public interface JAtomicResourceSession {
	
	/**
	 * only true is supported,otherwise the operation fail.
	 * @return
	 */
	boolean cached();
	
	
	/**
	 * commit resource.
	 * @throws Exception
	 */
	void commit() throws Exception;
	
	/**
	 * rollback resource.
	 * @throws Exception
	 */
	void rollback() throws Exception;
		
}
