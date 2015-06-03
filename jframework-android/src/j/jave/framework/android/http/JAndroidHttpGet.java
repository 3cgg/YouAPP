package j.jave.framework.android.http;

import j.jave.framework.extension.http.JHttpBase;
import j.jave.framework.utils.JCollectionUtils;
import j.jave.framework.utils.JStringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class JAndroidHttpGet extends JHttpBase<JAndroidHttpGet> {

	@Override
	public Object execute(String url) throws IOException {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		// set proxy
		if (JStringUtils.isNotNullOrEmpty(proxyHost)) {
			HttpHost proxy = new HttpHost(proxyHost, proxyPort);
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);
		}
		HttpParams httpParams= httpclient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
		HttpConnectionParams.setSoTimeout(httpParams, socketTimeout);
		
		// set target
		HttpGet httpGet = new HttpGet(url);

		// set query string.
		if(JCollectionUtils.hasInMap(params)){
			for (Iterator<Entry<String, Object>> iterator = params.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, Object> entry =  iterator.next();
				httpGet.getParams().setParameter(entry.getKey(), entry.getValue());
			}
		}
		
		// 执行
		HttpResponse response = httpclient.execute(httpGet);
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
		return null;
	}

}
