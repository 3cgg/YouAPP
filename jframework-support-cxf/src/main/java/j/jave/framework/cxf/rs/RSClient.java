package j.jave.framework.cxf.rs;

import j.jave.framework.commons.utils.JStringUtils;

import java.io.InputStream;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

public class RSClient {

	public static void main(String[] args) {
		WebClient webClient=WebClient.create("http://localhost:8111/ws/jaxrs");
		
		RSDemo rsDemo=new RSDemo();
		
		RSDemo response=  webClient.path("rsdemo/saveRsDemo")
				.accept(MediaType.APPLICATION_JSON)
				.acceptEncoding("utf-9")
				.type(MediaType.APPLICATION_JSON)
				.post(rsDemo,RSDemo.class);
		System.out.println(response.getName());
		
		webClient.reset();
		
		Response response2=  webClient.path("rsdemo/saveRsDemo")
				.accept(MediaType.APPLICATION_JSON)
				.acceptEncoding("utf-9")
				.type(MediaType.APPLICATION_JSON)
				.post(rsDemo);
		
		InputStream inputStream=(InputStream) response2.getEntity();
		
		byte[] bytes=JStringUtils.getBytes(inputStream);
		
		String string=new String(bytes);
		
		System.out.println(string);
	}
	
}
