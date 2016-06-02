package test.j.jave.platform.standalone;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.dataexchange.modelprotocol.JProtocolByteHandler;
import j.jave.kernal.dataexchange.modelprotocol.JProtocolObjectHandler;
import j.jave.kernal.jave.base64.JBase64;
import j.jave.kernal.jave.base64.JBase64FactoryProvider;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.model.JPageRequest;
import j.jave.platform.standalone.interimpl.DefaultMessageMetaSenderBuilder;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TestDataExchange extends test.j.jave.platform.standalone.TestEventSupport{
	
	protected JBase64 base64Service=JBase64FactoryProvider.getBase64Factory().getBase64();
	
	@Test
	public void testMul() throws Exception{
		
		for(int i=0;i<10;i++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try{
						testJSON();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}).start();
		}
		
	}
	
	static int count=100;
	
	@Test
	public void testJSON() throws Exception{
		try{
			
			
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("default", JConfiguration.get());
			map.put("a", "b");
			map.put("h", 90);
			
			JPageRequest pageRequest=new JPageRequest();
			pageRequest.setPageNumber(1111);
			pageRequest.setPageSize(9999);
			
			map.put("page", pageRequest);
			
			map.put("name", "BMW-"+count++);
			
			String jsonString=DefaultMessageMetaSenderBuilder.get()
			.setURL("http://127.0.0.1:8080/example/getCar")
			.putData(base64Service.encodeBase64String(JJSON.get().formatObject(map).getBytes("utf-8")))
			.setSendHandler(new JProtocolObjectHandler() {
				@Override
				public byte[] handle(Object data) throws Exception {
					return JJSON.get().formatObject(data).getBytes("utf-8");
				}
			})
			.setReceiveHandler(new JProtocolByteHandler() {
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
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
