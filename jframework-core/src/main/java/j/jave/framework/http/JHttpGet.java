package j.jave.framework.http;

import j.jave.framework.utils.JUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;


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
		return execute(url);
	}
	
	/* (non-Javadoc)
	 * @see j.jave.framework.http.JHttp#getHttpType()
	 */
	@Override
	protected HttpUriRequest getHttpType() {
		
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
				paramString=paramString+"&"+entry.getKey()+"="+JUtils.toString(entry.getValue());
			}
			if(JUtils.isNotNullOrEmpty(paramString)){
				// remove '&'
				paramString=paramString.substring(1);
			}
			try {
				url=url+"?"+URLEncoder.encode(paramString, "utf-8");
			} catch (UnsupportedEncodingException e) {
				// never occurs. 
			}
		}
		
		HttpGet httpGet=new HttpGet(url);
		return httpGet;
	}
}
