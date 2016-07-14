package j.jave.kernal.mock;

import j.jave.kernal.jave.service.JService;

import java.util.Map;

public interface JURIGetDataService extends JService{

	public Object getData(String uri,Map<String, Object>... params) throws Exception;
	
}
