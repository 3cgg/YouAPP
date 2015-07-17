package j.jave.framework.commons.xmldb.jql;

import j.jave.framework.commons.model.JBaseModel;

import java.util.Map;

public interface JValue extends JJQL {
	
	public Object value(Map<String, JBaseModel> models);
	
}
