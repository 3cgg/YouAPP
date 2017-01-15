package test.j.jave.kernal.dataexchange;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel._c.base64.JBase64;
import me.bunny.kernel._c.base64.JBase64FactoryProvider;
import me.bunny.kernel._c.json.JJSON;
import me.bunny.kernel._c.model.JSimplePageable;
import me.bunny.kernel.dataexchange.impl.JByteDecoder;
import me.bunny.kernel.dataexchange.impl.JDefaultMessageMetaSenderBuilder;
import me.bunny.kernel.dataexchange.impl.JEncoderRegisterService;
import me.bunny.kernel.dataexchange.impl.interimpl.JObjectTransModel;
import me.bunny.kernel.dataexchange.impl.interimpl.JObjectTransModelBuilder;
import me.bunny.kernel.dataexchange.impl.interimpl.JObjectTransModelProtocol;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;
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
		
		JSimplePageable pageRequest=new JSimplePageable();
		pageRequest.setPageNumber(1111);
		pageRequest.setPageSize(9999);
		
		String base64String=JObjectTransModelBuilder.get(JObjectTransModelProtocol.OBJECT)
		.putData("default", JConfiguration.get())
		.putData("a", "b")
		.putData("h", 90)
		.putData("page", pageRequest)
		.buildObjectTransModel();
		
		String jsonString=
				JDefaultMessageMetaSenderBuilder.get()
				.setBase64String(base64String)
				.setDataByteEncoder(JObjectTransModelProtocol.OBJECT.name())
				.setURL("http://localhost:8689/youapp/extapi/usermanager/getTimeline")
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
	
	
	@Test
	public void testJSON(){Map<String, Object> map=new HashMap<String, Object>();
		map.put("default", JConfiguration.get());
		map.put("a", "b");
		map.put("h", 90);
		
		JSimplePageable pageRequest=new JSimplePageable();
		pageRequest.setPageNumber(1111);
		pageRequest.setPageSize(9999);
		
		String base64String=JObjectTransModelBuilder.get(JObjectTransModelProtocol.OBJECT)
		.putData("default", JConfiguration.get())
		.putData("a", "b")
		.putData("h", 90)
		.putData("page", pageRequest)
		.buildObjectTransModel();
		
		String jsonString=
				JDefaultMessageMetaSenderBuilder.get()
				.setBase64String(base64String)
				.setDataByteEncoder(JObjectTransModelProtocol.JSON.name())
				.setURL("http://localhost:8689/youapp/extapi/usermanager/getTimeline")
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
	
	protected JBase64 base64Service=JBase64FactoryProvider.getBase64Factory().getBase64();
	
	
	@Test
	public void testProtocol(){
		try{
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("serviceContext", "");
		map.put("formData", "");
		map.put("paginationData", "");
		JObjectTransModel model=new JObjectTransModel();
		model.setProtocol(JObjectTransModelProtocol.JSON);
		model.setParams(map);
		model.setParser("simple");
		String base64String=base64Service.encodeBase64String(JJSON.get().formatObject(model).getBytes("utf-8"));
		
		String jsonString=JDefaultMessageMetaSenderBuilder.get()
		.setURL("http://localhost:8689/youapp/extapi/parammanager/getParamTypesByPage?code=M")
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
		
		System.out.println(jsonString);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
}
