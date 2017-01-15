package me.bunny.kernel._c.support.treeview;

import java.util.Map;

/**
 * any model that need represent tree view should implement the interface to
 * enable the function.
 * @author J
 *
 */
public interface JSimpleTreeStrcture {

	/**
	 * the identification of the model.
	 * @return
	 */
	public String getId();
	
	/**
	 * the represent name.
	 * @return
	 */
	public String getName();
	
	/**
	 * the identification of the parent model.
	 * @return
	 */
	public String getParentId();
	
	/**
	 * wrap this object to simple map, avoid reference to itself
	 * @return
	 */
	public Map<String, Object> toMap();
	
}
