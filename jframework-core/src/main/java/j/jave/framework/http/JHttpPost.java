package j.jave.framework.http;

import j.jave.framework.utils.JUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.HttpHost;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;


/**
 * POST method of HTTP.
 * @author Administrator
 *
 */
public class JHttpPost extends JHttp<JHttpPost> {
	
	/**
	 * url must be set by "setUrl" method . 
	 * @see setUrl  
	 * @return
	 * @throws IOException
	 */
	public Object post() throws IOException{
		return post(this.url);
	}
	
	public Object post(String url) throws IOException{
		HttpClientBuilder httpClientBuilder = HttpClients.custom();
		if(proxyHost!=null){
			HttpHost proxy = new HttpHost(proxyHost,proxyPort);
			DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
			httpClientBuilder.setRoutePlanner(routePlanner);
		}
		CloseableHttpClient closeableHttpClient= httpClientBuilder.build();
		HttpPost httpPost = new HttpPost(url);
		
		// add entry 
		if(entry!=null){
			httpPost.setEntity(new ByteArrayEntity(entry));
		}
		
		// add head 
		if(headers!=null){
			for (Iterator<Entry<String, String> > iterator = headers.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, String>  entry =  iterator.next();
				httpPost.addHeader(entry.getKey(), entry.getValue());
			}
		}
		
		CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
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
