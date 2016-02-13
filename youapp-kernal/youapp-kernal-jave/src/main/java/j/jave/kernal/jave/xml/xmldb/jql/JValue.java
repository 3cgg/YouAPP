package j.jave.kernal.jave.xml.xmldb.jql;

import j.jave.kernal.jave.model.JBaseModel;

import java.util.Map;

public interface JValue extends JJQL {
	
	public Object value(Map<String, JBaseModel> models);
	
}
