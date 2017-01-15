package me.bunny.kernel._c.xml.xmldb.jql;

import java.util.Map;

import me.bunny.kernel._c.model.JBaseModel;

public interface JValue extends JJQL {
	
	public Object value(Map<String, JBaseModel> models);
	
}
