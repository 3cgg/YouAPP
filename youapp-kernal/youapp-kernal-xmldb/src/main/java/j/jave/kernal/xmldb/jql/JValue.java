package j.jave.kernal.xmldb.jql;

import j.jave.kernal.jave.model.JBaseModel;

import java.util.Map;

public interface JValue extends JJQL {
	
	public Object value(Map<String, JBaseModel> models);
	
}
