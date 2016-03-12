package test.j.jave.kernal.dataexchange;

import java.util.HashMap;
import java.util.Map;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.dataexchange.protocol.JObjectTransModel;
import j.jave.kernal.dataexchange.protocol.JProtocol;
import j.jave.kernal.dataexchange.protocol.JProtocolSenderBuilder;
import j.jave.kernal.jave.model.JPageRequest;
import junit.framework.TestCase;

public class TestDataExchange extends TestCase{

	public void testDE(){
		
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
}
