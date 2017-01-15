package me.bunny.kernel.jave.xml.xmldb.jql;

import java.util.Map;

import me.bunny.kernel.jave.model.JBaseModel;

public interface JValue extends JJQL {
	
	public Object value(Map<String, JBaseModel> models);
	
}
