/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.jave.service.JService;

/**
 * any service provider need implement the factory to expose self.
 * @author J
 *
 */
public interface JServiceFactory<T extends JService> {
	/**
	 * get service object. 
	 * @return
	 */
	T getService();	
	
	/**
	 * get service name.
	 * @return
	 */
	String getName();
	
	/**
	 * get unique service identification .
	 * @return
	 */
	String getUniqueId();
	
	/**
	 * describer 
	 * @return
	 */
	String describer();
	
}
