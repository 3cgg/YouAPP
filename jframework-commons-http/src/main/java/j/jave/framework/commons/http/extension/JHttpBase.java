package j.jave.framework.commons.http.extension;

import j.jave.framework.commons.http.JHttpFile;
import j.jave.framework.commons.http.JResponseHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.entity.mime.MultipartEntityBuilder;



/**
 * some common implementations for subclasses extends from the HTTP. 
 * @author Administrator
 *
 * @param <T>
 */
public abstract class JHttpBase <T extends JHttpBase<T>>{
	
	protected String proxyHost;
	
	protected int proxyPort;
	
	protected String url ;
	
	protected int timeout=3000;
	
	protected int socketTimeout=30000;
	
	protected int retry=3;
	
	/**
	 * highest priority, it will  override the "params" or "files". 
	 */
	protected byte[] entry;
	
	protected Map<String, String> headers=new HashMap<String, String>();
	
	protected JResponseHandler<?> responseHandler=null;
	
	/**
	 * to indicate whether to encode the URL parameter or not.
	 */
	protected boolean encode=true;
	
	/**
	 * to indicate whether to encode the URL parameter or not.
	 * @param encode
	 */
	@SuppressWarnings("unchecked")
	public T setEncode(boolean encode) {
		this.encode = encode;
		return (T) this;
	}
	
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
	public final Object execute() throws IOException{
		return execute(this.url);
	}
	
	/**
	 * execute with passed url . 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public abstract Object execute(String url) throws IOException;
	
	@SuppressWarnings("unchecked")
	public T putFiles(List<JHttpFile> files){
		if(files!=null){
			this.files.addAll(files);
		}
		return (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T putFile(JHttpFile file){
		if(file!=null){
			this.files.add(file);
		}
		return (T) this;
	}
	
	
	/**
	 * set custom response handler, now default handler is String Handler, get the string format of response.
	 * @param responseHandler the responseHandler to set
	 */
	public void setResponseHandler(JResponseHandler<?> responseHandler) {
		this.responseHandler = responseHandler;
	}

	public JHttpBase<T> setTimeout(int timeout) {
		this.timeout = timeout;
		return this;
	}

	public JHttpBase<T> setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
		return this;
	}
	
	public JHttpBase<T>  setRetry(int retry) {
		this.retry = retry;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public T putHead(String key,String value) {
		headers.put(key, value);
		return  (T) this;
	}
	
	@SuppressWarnings("unchecked")
	public T putHeads(Map<String, String> heads) {
		if(headers!=null){
			this.headers.putAll(heads);
		}
		return  (T) this;
	}
	
}
