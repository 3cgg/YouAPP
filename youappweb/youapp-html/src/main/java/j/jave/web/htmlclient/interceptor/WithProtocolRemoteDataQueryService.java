package j.jave.web.htmlclient.interceptor;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.dataexchange.impl.JByteDecoder;
import j.jave.kernal.dataexchange.impl.JDefaultMessageMetaSenderBuilder;
import j.jave.kernal.dataexchange.impl.interimpl.JObjectTransModel;
import j.jave.kernal.dataexchange.impl.interimpl.JObjectTransModelProtocol;
import j.jave.kernal.jave.base64.JBase64;
import j.jave.kernal.jave.base64.JBase64FactoryProvider;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.web.htmlclient.WebHtmlClientProperties;
import j.jave.web.htmlclient.request.RequestVO;
import j.jave.web.htmlclient.response.ResponseModel;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class WithProtocolRemoteDataQueryService implements DataQueryService {

	protected JBase64 base64Service=JBase64FactoryProvider.getBase64Factory().getBase64();
		
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
					e.printStackTrace();
				}
				return object;
			}
		})
		.build().send();
		return JJSON.get().parse(jsonString, ResponseModel.class);
	}
	
}
