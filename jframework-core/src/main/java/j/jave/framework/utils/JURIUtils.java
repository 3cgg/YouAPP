/**
 * 
 */
package j.jave.framework.utils;

import j.jave.framework.extension.http.JHttpBase;
import j.jave.framework.extension.logger.JLogger;
import j.jave.framework.http.JHttpFactory;
import j.jave.framework.http.JResponseHandler;
import j.jave.framework.logging.JLoggerFactory;

import java.io.File;
import java.net.URI;

/**
 * @author J
 */
public abstract class JURIUtils {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(JURIUtils.class);
	
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
				JHttpBase<?> httpGet=JHttpFactory.getHttpGet();
				httpGet.setResponseHandler(new JResponseHandler<byte[]>(){
					@Override
					public byte[] process(byte[] bytes)
							throws j.jave.framework.http.JResponseHandler.ProcessException {
						return bytes;
					}
				});
				bytes= (byte[]) httpGet.execute(uriString);
			}
			return bytes;
		}catch(Exception e){
			LOGGER.error("cannot get bytes from "+uriString);
			throw new JUtilException(e);
		}
	}
	
	
}
