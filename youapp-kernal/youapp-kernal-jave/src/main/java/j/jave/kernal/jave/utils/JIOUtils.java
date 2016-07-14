package j.jave.kernal.jave.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public abstract class JIOUtils {

	public static void write(File file,byte[] bytes){
		FileOutputStream fileOutputStream=null;
		try{
			fileOutputStream=new FileOutputStream(file);
			fileOutputStream.getChannel().write(ByteBuffer.wrap(bytes));
			fileOutputStream.flush();
		}catch(Exception e){
			throw new JUtilException(e);
		}finally{
			if(fileOutputStream!=null){
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					throw new JUtilException(e);
				}
			}
		}
	}
	
	public static void write(File file,InputStream inputStream){
		write(file, getBytes(inputStream));
	}
	
	/**
	 * extract all bytes from the {@link InputStream}. 	
	 * @param input
	 * @return
	 * @throws JUtilException	
	 */
	public static byte[] getBytes(InputStream input) {
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    byte[] buffer = new byte[4096];
	    int n = 0;
	    try {
			while (-1 != (n = input.read(buffer))) {
			    output.write(buffer, 0, n);
			}
			output.flush();
		} catch (IOException e) {
			throw new JUtilException(e);
		}
	    return output.toByteArray();
	}
	
	/**
	 * extract all bytes from the {@link InputStream}. 	
	 * @param input
	 * @param close close the stream if true
	 * @return
	 * @throws JUtilException	
	 */
	public static byte[] getBytes(InputStream input,boolean close){
		try{
			return getBytes(input);
		}finally{
			if(close&&input!=null){
				try {
					input.close();
				} catch (IOException e) {
					throw new JUtilException(e);
				}
			}
		}
	} 
}
