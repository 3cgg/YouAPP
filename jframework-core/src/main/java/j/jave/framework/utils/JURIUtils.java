/**
 * 
 */
package j.jave.framework.utils;

import j.jave.framework.http.JHttpGet;
import j.jave.framework.http.JResponseHandler;

import java.io.File;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author J
 */
public abstract class JURIUtils {

	private static final Logger LOGGER=LoggerFactory.getLogger(JURIUtils.class);
	
	private static final String SCHEMA_HTTP="http";
	private static final String SCHEMA_FILE="file";
	
	
	/**
	 * get byte array from the URI.  test if the URI can convert to URL, or File System. 
	 * @param uriString
	 * @return byte[]
	 */
	public static byte[] getBytes(String uriString){
		try{
			URI uri=new URI(uriString);
			byte[] bytes=null;
			String schema=uri.getScheme();
			if(SCHEMA_FILE.equalsIgnoreCase(schema)){
				bytes=JFileUtils.getBytes(new File(uri));
			}
			else if(SCHEMA_HTTP.equalsIgnoreCase(schema)){
				JHttpGet httpGet=new JHttpGet();
				httpGet.setResponseHandler(new JResponseHandler<byte[]>(){
					@Override
					public byte[] process(byte[] bytes)
							throws j.jave.framework.http.JResponseHandler.ProcessException {
						return bytes;
					}
				});
				bytes= (byte[]) httpGet.get(uriString);
			}
			return bytes;
		}catch(Exception e){
			LOGGER.error("cannot get bytes from "+uriString);
			throw new JUtilException(e);
		}
	}
	
	
}
