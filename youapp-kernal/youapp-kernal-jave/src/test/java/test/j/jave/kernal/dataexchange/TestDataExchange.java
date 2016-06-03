package test.j.jave.kernal.dataexchange;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.dataexchange.modelprotocol.JByteDecoder;
import j.jave.kernal.dataexchange.modelprotocol.JEncoderRegisterService;
import j.jave.kernal.dataexchange.modelprotocol.interimpl.JObjectTransModelSenderBuilder;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.model.JPageRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import test.j.jave.kernal.eventdriven.TestEventSupport;

public class TestDataExchange extends TestEventSupport{
	
	private JEncoderRegisterService encoderRegisterService
	=JServiceHubDelegate.get().getService(this, JEncoderRegisterService.class);
	
	@Test
	public void testObject() throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("default", JConfiguration.get());
		map.put("a", "b");
		map.put("h", 90);
		
		JPageRequest pageRequest=new JPageRequest();
		pageRequest.setPageNumber(1111);
		pageRequest.setPageSize(9999);
		
		String jsonString=JObjectTransModelSenderBuilder.get()
		.setURL("http://localhost:8689/youapp/extapi/usermanager/getTimeline")
		.putData(String.class, "N")
		.build().send();
		System.out.println(jsonString);
	}
	
	
	@Test
	public void testJSON(){
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("default", JConfiguration.get());
		map.put("a", "b");
		map.put("h", 90);
		
		JConfiguration configuration=JConfiguration.get();
		
		JPageRequest pageRequest=new JPageRequest();
		pageRequest.setPageNumber(1111);
		pageRequest.setPageSize(9999);
		
		String jsonString=JObjectTransModelSenderBuilder.get()
		.setURL("http://localhost:8689/youapp/userManager/saveUser")
		.putData(JConfiguration.class, configuration)
		.putData(HashMap.class, map)
		.putData(JPageRequest.class, pageRequest)
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
		System.out.println(jsonString);
	}
}
