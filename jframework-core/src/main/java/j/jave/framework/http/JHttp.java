package j.jave.framework.http;

import j.jave.framework.utils.JStringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpHost;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;



/**
 * Super class for the HTTP . 
 * @author Administrator
 *
 * @param <T>
 */
public abstract class JHttp <T extends JHttp<?>>{

	private T type=(T) this;
	
	protected String proxyHost;
	protected int proxyPort;
	
	protected String url ;
	
	/**
	 * highest priority, it will  override the "params" or "files". 
	 */
	protected byte[] entry;
	
	protected Map<String, String> headers;
	
	protected JResponseHandler<?> responseHandler=null;
	
	/**
	 * form parameters for get or post . 
	 * i.e.  get style : ?a=b&c=d
	 * parsed with {@code MultipartEntityBuilder}
	 * see {@link MultipartEntityBuilder} 
	 */
	protected Map<String, Object> params=new ConcurrentHashMap<String, Object>(16);
	
	/**
	 * parsed with {@code MultipartEntityBuilder}
	 * see {@link MultipartEntityBuilder} 
	 */
	protected List<JHttpFile> files=new ArrayList<JHttpFile>(1);
	
	protected static JResponseHandler<String> stringJResponseHandler=new JResponseHandler<String>() {
		@Override
		public String process(byte[] bytes)  throws ProcessException {
			try {
				return new String(bytes,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new ProcessException(e);
			}
		}
	};
	
	@SuppressWarnings("unchecked")
	public T setProxy(String host,int port) {
		this.proxyHost=host;
		this.proxyPort=port;
		return  (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T setUrl(String url) {
		this.url = url;
		return  (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T setEntry(byte[] entry) {
		this.entry = entry;
		return  (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T putParam(String key,Object value) {
		params.put(key, value);
		return  (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T putParams(Map<String, Object> params) {
		if(params!=null){
			this.params.putAll(params);
		}
		return  (T) this;
	}
	
	/**
	 * execute with constructed url . 
	 * @return
	 * @throws IOException
	 */
	protected Object execute() throws IOException{
		return execute(this.url);
	}
	
	/**
	 * execute with passed url . 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	protected Object execute(String url) throws IOException{
		HttpClientBuilder httpClientBuilder = getHttpClientBuilder();
		CloseableHttpClient closeableHttpClient= httpClientBuilder.build();
		
		// get special http request 
		HttpUriRequest httpUriRequest= getHttpType();
		
		// add head 
		if (headers != null) {
			for (Iterator<Entry<String, String>> iterator = headers.entrySet()
					.iterator(); iterator.hasNext();) {
				Entry<String, String> entry = iterator.next();
				httpUriRequest.addHeader(entry.getKey(), entry.getValue());
			}
		}
		
		CloseableHttpResponse response = closeableHttpClient.execute(httpUriRequest);
		try {
			StatusLine statusLine=response.getStatusLine();
			if(statusLine.getStatusCode()==200){
				InputStream inputStream=response.getEntity().getContent();
				try{
					byte[] bytes=JStringUtils.getBytes(inputStream);
					if(responseHandler==null)
						return stringJResponseHandler.process(bytes);
					else
						return responseHandler.process(bytes);
				}finally{
					inputStream.close();
				}
			} 
		}finally {
			response.close();
		}
		return null;
	}
	
	/**
	 * get HTTP request style . GET or POST 
	 * @return
	 */
	protected abstract HttpUriRequest getHttpType() ;
	
	
	protected HttpClientBuilder getHttpClientBuilder() {
		HttpClientBuilder httpClientBuilder = HttpClients.custom();
		configureHttpClientBuilder(httpClientBuilder);
		return httpClientBuilder;
	}

	private void configureHttpClientBuilder(HttpClientBuilder httpClientBuilder) {
		if(proxyHost!=null){
			HttpHost proxy = new HttpHost(proxyHost,proxyPort);
			DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
			httpClientBuilder.setRoutePlanner(routePlanner);
		}
	}
	
	public T putFiles(List<JHttpFile> files){
		if(files!=null){
			this.files.addAll(files);
		}
		return  (T) this;
	}
	
	public T putFile(JHttpFile file){
		if(file!=null){
			this.files.add(file);
		}
		return  (T) this;
	}
	
}
