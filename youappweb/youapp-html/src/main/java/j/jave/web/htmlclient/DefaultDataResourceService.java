package j.jave.web.htmlclient;

import java.util.HashMap;
import java.util.Map;

public class DefaultDataResourceService implements DataResourceService {

	private static final Map<String, Object> M=new HashMap<String, Object>();
	
	@Override
	public Map<String, Object> data(String dataUrl) {
		return M;
	}
	
}
