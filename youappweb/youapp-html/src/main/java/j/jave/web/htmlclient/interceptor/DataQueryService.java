package j.jave.web.htmlclient.interceptor;

import j.jave.kernal.jave.service.JService;
import j.jave.web.htmlclient.request.RequestVO;

public interface DataQueryService extends JService {

	public Object query(RequestVO requestVO)throws Exception;
	
}
