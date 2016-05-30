package test.j.jave.kernal.dataexchange;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.dataexchange.protocol.JObjectTransModelSenderBuilder;
import j.jave.kernal.dataexchange.protocol.JProtocol;
import j.jave.kernal.dataexchange.protocol.JProtocolByteHandler;
import j.jave.kernal.eventdriven.servicehub.JServiceFactoryManager;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.model.JPageRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import test.j.jave.kernal.eventdriven.TestEventSupport;

public class TestDataExchange extends TestEventSupport{

	private JServiceFactoryManager serviceFactoryManager=JServiceFactoryManager.get();

	protected JServiceHubDelegate serviceHubDelegate=JServiceHubDelegate.get();
	
	@Before
	public void initialize() throws Exception {
		serviceFactoryManager.registerAllServices();
	}
	
	@Test
	public void testObject() throws Exception{
		
		initialize();
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("default", JConfiguration.get());
		map.put("a", "b");
		map.put("h", 90);
		
		JConfiguration configuration=JConfiguration.get();
		
		JPageRequest pageRequest=new JPageRequest();
		pageRequest.setPageNumber(1111);
		pageRequest.setPageSize(9999);
		
		String jsonString=JObjectTransModelSenderBuilder.get(JProtocol.JSON)
		.setURL("http://localhost:8689/youapp/userManager/saveUser")
		.putData(JConfiguration.class, configuration)
		.putData(HashMap.class, map)
		.putData(JPageRequest.class, pageRequest)
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
		
		String jsonString=JObjectTransModelSenderBuilder.get(JProtocol.JSON)
		.setURL("http://localhost:8689/youapp/userManager/saveUser")
		.putData(JConfiguration.class, configuration)
		.putData(HashMap.class, map)
		.putData(JPageRequest.class, pageRequest)
		.setProtocolByteHandler(new JProtocolByteHandler() {
			
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
