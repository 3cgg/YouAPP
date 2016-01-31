/**
 * 
 */
package j.jave.kernal.jave.utils;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;

/**
 * @author J
 */
public abstract class JURIUtils {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(JURIUtils.class);
	
	private static final String SCHEMA_HTTP="http";
	private static final String SCHEMA_FILE="file";
	private static final String SLASH="/";
	
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
				URLConnection urlConnection= uri.toURL().openConnection();
				InputStream inputStream = urlConnection.getInputStream();
				try{
				bytes=JStringUtils.getBytes(inputStream);
				}finally{
					inputStream.close();
				}
			}
			return bytes;
		}catch(Exception e){
			LOGGER.error("cannot get bytes from "+uriString);
			throw new JUtilException(e);
		}
	}
	
	/**
	 * create a new path with the base and input path
	 * @param baseDir
	 * @param path
	 * @return
	 */
	public static String append(String baseDir,String path){
		String trimBaseDir=baseDir.trim();
		if(trimBaseDir.endsWith(SLASH)){
			return trimBaseDir+path;
		}
		else{
			return trimBaseDir+SLASH+path;
		}
	}
	
	
	
}
