package j.jave.framework.http;

import j.jave.framework.utils.JUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.HttpHost;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;


/**
 * GET method of HTTP
 * @author Administrator
 *
 */
public class JHttpGet extends JHttp<JHttpGet> {
	
	
	/**
	 * url must be set by "setUrl" method . 
	 * @see setUrl  
	 * @return
	 * @throws IOException
	 */
	public Object get() throws IOException{
		return get(this.url);
	}
	
	public Object get(String url) throws IOException{
		HttpClientBuilder httpClientBuilder = HttpClients.custom();
		if(proxyHost!=null){
			HttpHost proxy = new HttpHost(proxyHost,proxyPort);
			DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
			httpClientBuilder.setRoutePlanner(routePlanner);
		}
		CloseableHttpClient closeableHttpClient= httpClientBuilder.build();
		HttpGet httpget = new HttpGet(url);
		
		// add head
		if(headers!=null){
			for (Iterator<Entry<String, String> > iterator = headers.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, String>  entry =  iterator.next();
				httpget.addHeader(entry.getKey(), entry.getValue());
			}
		}
		
		CloseableHttpResponse response = closeableHttpClient.execute(httpget);
		try {
			StatusLine statusLine=response.getStatusLine();
			if(statusLine.getStatusCode()==200){
				InputStream inputStream=response.getEntity().getContent();
				byte[] bytes=JUtils.getBytes(inputStream);
				if(responseHandler==null)
					return stringJResponseHandler.process(bytes);
				else
					return responseHandler.process(bytes);
			} 
		}finally {
			response.close();
		}
		return null;
	}
	
}
