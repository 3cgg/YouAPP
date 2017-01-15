package me.bunny.kernel._c.xml.xmldb.jql;

import java.io.Serializable;
import java.util.Map;

import me.bunny.kernel._c.model.JBaseModel;

public interface JJQL extends Cloneable , Serializable{
	
	
	/**
	 * print string
	 * @return
	 */
	public String jql();
	
	/**
	 * validate whether the element can right reflect the xml.
	 * @param from
	 */
	public void validateExpress(Map<String, Class<? extends JBaseModel>> from);
	
	public abstract Object clone() throws CloneNotSupportedException;
}
