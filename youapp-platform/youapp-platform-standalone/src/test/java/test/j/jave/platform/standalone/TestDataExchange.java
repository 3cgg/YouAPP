package test.j.jave.platform.standalone;

import j.jave.kernal.dataexchange.impl.JByteDecoder;
import j.jave.kernal.dataexchange.impl.JDefaultMessageMetaSenderBuilder;
import j.jave.kernal.dataexchange.impl.JEncoderRegisterService;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.eventdriven.servicehub.notify.JEventRequestStartNotifyEvent;
import j.jave.kernal.eventdriven.servicehub.notify.JServiceAddNotifyEvent;
import j.jave.kernal.jave.base64.JBase64;
import j.jave.kernal.jave.base64.JBase64FactoryProvider;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.model.JPageRequest;
import j.jave.kernal.jave.utils.JUniqueUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TestDataExchange extends test.j.jave.platform.standalone.TestEventSupport{
	
	private JEncoderRegisterService encoderRegisterService
	=JServiceHubDelegate.get().getService(this, JEncoderRegisterService.class);
	
	protected JBase64 base64Service=JBase64FactoryProvider.getBase64Factory().getBase64();
	
	@Test
	public void testMul() throws Exception{
		int ooo=0;
		System.out.println(ooo);
		for(int i=0;i<173;i++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try{
						testJSON();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			},"call-th"+i).start();
		}
		
		System.out.println("end...................");
	}
	
	static int count=100;
	
	@Test
	public void testJSON() throws Exception{
		try{
			
			
			Map<String, Object> map=new HashMap<String, Object>();
			map.put(JUniqueUtils.unique(), JUniqueUtils.unique());
			map.put("a", "b");
			map.put("h", 90);
			
			JPageRequest pageRequest=new JPageRequest();
			pageRequest.setPageNumber(1111);
			pageRequest.setPageSize(9999);
			
			map.put("page", pageRequest);
			synchronized (this) {
				map.put("name", "BMW-"+count++);
				System.out.println("============>"+map.get("name"));
			}
			
			String base64String=base64Service.encodeBase64String(JJSON.get().formatObject(map).getBytes("utf-8"));
			
			String jsonString=JDefaultMessageMetaSenderBuilder.get()
			.setURL("http://127.0.0.1:8080/unit/getUnitName")
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
			System.out.println("-----------------------res------"+jsonString);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	@Test
	public void testaaa(){
		
		for(int i=0;i<11;i++){
			JServiceHubDelegate.get().addDelayEvent(new JEventRequestStartNotifyEvent(this, new JServiceAddNotifyEvent(this, this.getClass())));
			
		}
		
		
		System.out.println("end");
	}
	
	
	
	
	
	
	
	
}
