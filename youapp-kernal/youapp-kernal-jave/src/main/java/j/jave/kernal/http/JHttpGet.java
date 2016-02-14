package j.jave.kernal.http;

import j.jave.kernal.jave.utils.JStringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;


/**
 * GET method of HTTP
 * @author Administrator
 *
 */
class JHttpGet extends JHttp<JHttpGet> {
	
	
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
		return execute(url);
	}
	
	/* (non-Javadoc)
	 * @see j.jave.framework.http.JHttp#getHttpType()
	 */
	@Override
	protected HttpRequestBase getHttpType() {
		
		String url=this.url;
		if(params!=null){
			// useless 
//			List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
//			for (Iterator<Entry<String, Object>>  iterator = params.entrySet().iterator(); iterator.hasNext();) {
//				Entry<String, Object> entry =  iterator.next();
//				nameValuePairs.add(new BasicNameValuePair(entry.getKey(), JUtils.toString(entry.getValue())));
//			}
//			UrlEncodedFormEntity encodedFormEntity=new UrlEncodedFormEntity(nameValuePairs, Charset.forName("utf-8"));
			String paramString="";
			for (Iterator<Entry<String, Object>>  iterator = params.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, Object> entry =  iterator.next();
				try {
					String value=JStringUtils.toString(entry.getValue());
				paramString=paramString+"&"+entry.getKey()+"="+
						(encode?URLEncoder.encode(value, "utf-8"):value)
						;
				} catch (UnsupportedEncodingException e) {
					// never occurs. 
					throw new RuntimeException(e);
				}
			}
			if(JStringUtils.isNotNullOrEmpty(paramString)){
				// remove '&'
				paramString=paramString.substring(1);
			}
			url=JStringUtils.isNotNullOrEmpty(paramString)?
					(url+"?"+paramString)
					:url;
		}
		
		HttpGet httpGet=new HttpGet(url);
		return httpGet;
	}
}
