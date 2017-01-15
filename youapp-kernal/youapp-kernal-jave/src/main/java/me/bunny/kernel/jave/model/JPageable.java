/**
 * 
 */
package me.bunny.kernel.jave.model;

import java.io.Serializable;

/**
 * @author J
 */
public interface JPageable extends JCriteria, Serializable{

	/**
	 * Returns the page to be returned.
	 * 
	 * @return the page to be returned.
	 */
	int getPageNumber();

	/**
	 * Returns the number of items to be returned.
	 * 
	 * @return the number of items of that page
	 */
	int getPageSize();
	
	/**
	 * Returns the sorting parameters.
	 * @return 
	 */
	JOrder[] getOrders();
	
	
	String getOrder();
	
}
