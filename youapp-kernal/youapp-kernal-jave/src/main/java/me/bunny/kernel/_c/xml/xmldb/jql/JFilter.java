package me.bunny.kernel._c.xml.xmldb.jql;

import java.util.Map;

import me.bunny.kernel._c.model.JBaseModel;

public interface JFilter extends JJQL {
	
	/**
	 * return the name of current filter.
	 * @return
	 */
	public String name();
	
	
	/**
	 * push a filter , only success for {@link JGroup}
	 * an unexpected exception thrown for {@link If} or {@link JLink}
	 * @param filter
	 */
	public void push(JFilter filter);
	
	/**
	 * null equals empty.
	 * @param models
	 * @return
	 */
	public boolean validate(Map<String, JBaseModel> models);
	
}
