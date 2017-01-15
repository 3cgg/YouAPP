package j.jave.web.htmlclient.interceptor;

import j.jave.web.htmlclient.WebHtmlClientProperties;
import j.jave.web.htmlclient.request.RequestVO;
import j.jave.web.htmlclient.response.ResponseModel;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel._c.base64.JBase64;
import me.bunny.kernel._c.base64.JBase64FactoryProvider;
import me.bunny.kernel._c.json.JJSON;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.utils.JAssert;
import me.bunny.kernel.dataexchange.impl.JByteDecoder;
import me.bunny.kernel.dataexchange.impl.JDefaultMessageMetaSenderBuilder;
import me.bunny.kernel.dataexchange.impl.interimpl.JObjectTransModel;
import me.bunny.kernel.dataexchange.impl.interimpl.JObjectTransModelProtocol;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class WithProtocolRemoteDataQueryService implements DataQueryService {

	protected JBase64 base64Service=JBase64FactoryProvider.getBase64Factory().getBase64();
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(WithProtocolRemoteDataQueryService.class);
	
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
		
		String formData=requestVO.getFormData();
		
		String paginationData=requestVO.getPaginationData();
		
		String serviceContext=requestVO.getServiceContext() ;
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("_youapp_serviceContext", serviceContext);
		map.put("_youapp_formData", formData);
		map.put("_youapp_paginationData", paginationData);
		
		Map<String, Object> formDatas= JJSON.get().parse(formData);
		for(Entry<String, Object> entry:formDatas.entrySet()){
			map.put(entry.getKey(), entry.getValue());
		}
		
		map.put("_youapp_ticket", requestVO.getTicket());
		
		JObjectTransModel model=new JObjectTransModel();
		model.setProtocol(JObjectTransModelProtocol.JSON);
		model.setParams(map);
		model.setParser("simple");
		String base64String=base64Service.encodeBase64String(JJSON.get().formatObject(model).getBytes("utf-8"));
		
		String jsonString=JDefaultMessageMetaSenderBuilder.get()
		.setURL(url)
		.setBase64String(base64String)
		.setDataByteEncoder("JSON")
		.setReceiveByteDecoder(new JByteDecoder() {
			@Override
			public Object decode(byte[] bytes) {
				Object object=null;
				try {
					object=new String(bytes,"utf-8");
				} catch (UnsupportedEncodingException e) {
					LOGGER.error(e.getMessage(), e);
				}
				return object;
			}
		})
		.build().send();
		return JJSON.get().parse(jsonString, ResponseModel.class);
	}
	
}
