package j.jave.framework.http;

import java.io.UnsupportedEncodingException;
import java.util.Map;



/**
 * Super class for the HTTP . 
 * @author Administrator
 *
 * @param <T>
 */
public abstract class JHttp <T extends JHttp<?>>{

	protected String proxyHost;
	protected int proxyPort;
	
	protected String url ;
	
	protected byte[] entry;
	
	protected Map<String, String> headers;
	
	protected JResponseHandler<?> responseHandler=null;
	
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
}
