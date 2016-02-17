package j.jave.securityutil.securityclient;

import j.jave.kernal.http.JHttpFactoryProvider;
import j.jave.kernal.jave.io.JFile;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JFileUtils;
import j.jave.kernal.jave.utils.JPropertiesUtils;
import j.jave.kernal.jave.utils.JStringUtils;

import java.util.Properties;

public class JRSClient {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(JRSClient.class);
	
	private static JRSClient client;
	
	private String endpointUrl;
	
	private int timeout=3000;
	
	private int retry=3;
	
	public static JRSClient get(){
		
		if(client==null){
			synchronized (JRSClient.class) {
				if(client==null){
					client=new JRSClient();
					
					Properties properties= JPropertiesUtils.loadProperties(new JFile(JFileUtils.getFileFromClassPath("inner-support-ws-rs-client.properties")));
					
					String endpointUrl=JPropertiesUtils.getKey("j.jave.framework.inner.support.ws.rs.url", properties);
					JAssert.state(JStringUtils.isNotNullOrEmpty(endpointUrl), "url is missing,please check your webservice config(inner-support-ws-rs-client.properties).");
					client.endpointUrl=endpointUrl.trim();
					
					LOGGER.info("wsclinet : ws endpoint url : "+client.endpointUrl); 
					
					String timeout=JPropertiesUtils.getKey("j.jave.framework.inner.support.ws.rs.timeout", properties);
					if(JStringUtils.isNotNullOrEmpty(timeout)){
						client.timeout=Integer.parseInt(timeout)*1000;
					}
					
					String retry=JPropertiesUtils.getKey("j.jave.framework.inner.support.ws.rs.retry", properties);
					if(JStringUtils.isNotNullOrEmpty(retry)){
						client.retry=Integer.parseInt(retry);
					}
					
				}
			}
		}
		return client;
	}
	
	public Object get(String url) throws Exception{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("GET REQUEST URL :  "+ endpointUrl+url);
		}
		return JHttpFactoryProvider.getHttpFactory().getHttpGet().setTimeout(timeout).setRetry(retry).setUrl(endpointUrl+url).execute();
	}
	
	public Object post(String url,String entity) throws Exception{
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("POST REQUEST URL :  "+ endpointUrl+url);
		}
		return JHttpFactoryProvider.getHttpFactory().getHttpPost().setTimeout(timeout).setRetry(retry).setUrl(endpointUrl+url).setEntry(entity.getBytes("utf-8")).execute();
	} 
	
}
