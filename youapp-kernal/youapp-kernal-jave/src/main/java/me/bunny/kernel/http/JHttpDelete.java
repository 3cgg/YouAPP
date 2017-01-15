package me.bunny.kernel.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpRequestBase;

import me.bunny.kernel.jave.utils.JStringUtils;


/**
 * DELETE method of HTTP
 * @author Administrator
 *
 */
class JHttpDelete extends JHttp<JHttpDelete> {
	
	
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
				paramString=paramString+"&"+entry.getKey()+"="+JStringUtils.toString(entry.getValue());
			}
			if(JStringUtils.isNotNullOrEmpty(paramString)){
				// remove '&'
				paramString=paramString.substring(1);
			}
			try {
				url=JStringUtils.isNotNullOrEmpty(paramString)?(url+"?"+URLEncoder.encode(paramString, "utf-8")):url;
			} catch (UnsupportedEncodingException e) {
				// never occurs. 
			}
		}
		
		HttpDelete httpDelete=new HttpDelete(url);
		return httpDelete;
	}
}
