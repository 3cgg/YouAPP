package j.jave.framework.http;

import j.jave.framework.utils.JUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;


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
		return execute(url);
		
	}
	
	/* (non-Javadoc)
	 * @see j.jave.framework.http.JHttp#getHttpType()
	 */
	@Override
	protected HttpUriRequest getHttpType() {
		MultipartEntityBuilder multipartEntityBuilder=null;
		if(params!=null){
			multipartEntityBuilder=MultipartEntityBuilder.create();
			for (Iterator<Entry<String, Object>>  iterator = params.entrySet().iterator(); iterator.hasNext();) {
				Entry<String, Object> entry =  iterator.next();
				multipartEntityBuilder.addTextBody(entry.getKey(), JUtils.toString(entry.getValue()));
			}
			multipartEntityBuilder.setCharset(Charset.forName("utf-8"));
		}
		
		if(files!=null){
			if(multipartEntityBuilder==null){
				multipartEntityBuilder=MultipartEntityBuilder.create();
			}
			for(int i=0;i<files.size();i++){
				JHttpFile jHttpFile=files.get(i);
				multipartEntityBuilder.addBinaryBody(jHttpFile.getAttrName(), jHttpFile.getFileContent(),
						ContentType.DEFAULT_BINARY, jHttpFile.getFilename());
			}
		}
		
		HttpPost httpPost = new HttpPost(url);
		
		if(multipartEntityBuilder!=null){
			httpPost.setEntity(multipartEntityBuilder.build());
		}
		
		// add entry
		if(entry!=null){
			httpPost.setEntity(new ByteArrayEntity(entry));
		}
		
		return httpPost;
	}
	
	
}
