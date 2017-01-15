package j.jave.web.htmlclient.interceptor;

import j.jave.web.htmlclient.WebHtmlClientProperties;
import j.jave.web.htmlclient.request.RequestVO;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.container.JContainerDelegate;
import me.bunny.kernel.container.JResourceContainer;
import me.bunny.kernel.container.JResourceContainerConfig;
import me.bunny.kernel.container.JExecutableURIUtil.Type;
import me.bunny.kernel.jave.json.JJSON;
import me.bunny.kernel.jave.utils.JAssert;

import java.net.URI;

public class DefaultRemoteDataQueryService implements DataQueryService {

	JResourceContainer resourceContainer= null;
	
	public JResourceContainer getResourceContainer() {
		if(resourceContainer==null){
			resourceContainer= JContainerDelegate.get().getContainer(JResourceContainerConfig.DEFAULT_UNIQUE);
		}
		return resourceContainer;
	} 
	
	private String host=null;
	{
		host=JConfiguration.get().getString(WebHtmlClientProperties.YOUAPPMVC_DATA_QUERY_REMOTE_HOST);
		JAssert.isNotEmpty(host,WebHtmlClientProperties.YOUAPPMVC_DATA_QUERY_REMOTE_HOST+"is empty.");
		if(!host.endsWith("/")){
			host=host+"/";
		}
	}
	@Override
	public Object query(RequestVO requestVO) throws Exception{
		String url=host+requestVO.getEndpoint();
		Object data=getResourceContainer().execute(new URI(url), getResourceContainer().newExecutionContext(Type.PUT)
				.setData(JJSON.get().formatObject(requestVO)));
		return data;
	}
	
}
