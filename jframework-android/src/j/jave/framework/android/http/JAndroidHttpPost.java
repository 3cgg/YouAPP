package j.jave.framework.android.http;

import j.jave.framework.extension.http.JHttpBase;
import j.jave.framework.http.JHttpFile;
import j.jave.framework.utils.JCollectionUtils;
import j.jave.framework.utils.JStringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class JAndroidHttpPost extends JHttpBase<JAndroidHttpPost> {

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
		HttpPost httpPost = new HttpPost(url);

		MultipartEntity mpEntity = new MultipartEntity();  
        
        if(JCollectionUtils.hasInMap(params)){
			for (Iterator<Entry<String, Object>> iterator = params.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, Object> entry =  iterator.next();
				FormBodyPart formBodyPart=new FormBodyPart(entry.getKey(), new StringBody(JStringUtils.toString(entry.getValue()),Charset.forName("UTF-8")));  
				mpEntity.addPart(formBodyPart);
			}
		}
        
        if(JCollectionUtils.hasInCollect(files)){
			for(int i=0;i<files.size();i++){
				JHttpFile jHttpFile=files.get(i);
				FormBodyPart formBodyPart=new FormBodyPart(jHttpFile.getAttrName(),
						new FileBody(jHttpFile.getFile(),"application/octet-stream"));
				mpEntity.addPart(formBodyPart);
			}
		}
        
     // add entry
		if (entry != null) {
			httpPost.setEntity(new ByteArrayEntity(entry));
		}
		else{
			httpPost.setEntity(mpEntity);  
		}
		// execute 
		HttpResponse response = httpclient.execute(httpPost);
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
