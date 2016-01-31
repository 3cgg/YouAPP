package j.jave.kernal.xmldb.jql;

import j.jave.kernal.jave.model.JBaseModel;

import java.util.Map;

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
