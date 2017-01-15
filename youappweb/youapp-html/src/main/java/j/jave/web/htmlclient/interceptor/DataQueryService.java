package j.jave.web.htmlclient.interceptor;

import j.jave.web.htmlclient.request.RequestVO;
import me.bunny.kernel.jave.service.JService;

public interface DataQueryService extends JService {

	public Object query(RequestVO requestVO)throws Exception;
	
}
