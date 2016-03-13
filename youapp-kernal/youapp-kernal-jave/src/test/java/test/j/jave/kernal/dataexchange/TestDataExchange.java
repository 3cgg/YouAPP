package test.j.jave.kernal.dataexchange;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.dataexchange.protocol.JProtocol;
import j.jave.kernal.dataexchange.protocol.JProtocolResultHandler;
import j.jave.kernal.dataexchange.protocol.JProtocolSenderBuilder;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.model.JPageRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class TestDataExchange extends TestCase{

	public void testObject(){
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("default", JConfiguration.get());
		map.put("a", "b");
		map.put("h", 90);
		
		JConfiguration configuration=JConfiguration.get();
		
		JPageRequest pageRequest=new JPageRequest();
		pageRequest.setPageNumber(1111);
		pageRequest.setPageSize(9999);
		
		String jsonString=JProtocolSenderBuilder.get(JProtocol.OBJECT)
		.setURL("http://localhost:8689/youapp/userManager/saveUser")
		.putData(JConfiguration.class, configuration)
		.putData(HashMap.class, map)
		.putData(JPageRequest.class, pageRequest)
		.setExptectedProtocol(JProtocol.JSON)
		.build().send();
		System.out.println(jsonString);
	}
	
	
	public void testJSON(){
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("default", JConfiguration.get());
		map.put("a", "b");
		map.put("h", 90);
		
		JConfiguration configuration=JConfiguration.get();
		
		JPageRequest pageRequest=new JPageRequest();
		pageRequest.setPageNumber(1111);
		pageRequest.setPageSize(9999);
		
		String jsonString=JProtocolSenderBuilder.get(JProtocol.JSON)
		.setURL("http://localhost:8689/youapp/userManager/saveUser")
		.putData(JConfiguration.class, configuration)
		.putData(HashMap.class, map)
		.putData(JPageRequest.class, pageRequest)
		.setExptectedProtocol(JProtocol.JSON)
		.setProtocolResultHandler(new JProtocolResultHandler() {
			
			@Override
			public Object handle(byte[] bytes) {
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
		System.out.println(jsonString);
	}
}
