package j.jave.platform.basicwebcomp.web.util;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * memory response wrapper , to make the page store into a certain location. 
 * <p>{@code CharArrayWriter } do with char response , such as JSP render.
 * <p>{@code ByteArrayOutputStream } do with binary response , such as JS,CSS etc.
 * @author J
 *
 */
public class JMemoryResponseWrapper extends HttpServletResponseWrapper {

	private static final Logger LOGGER=LoggerFactory.getLogger(JMemoryResponseWrapper.class);
	
	private ByteArrayOutputStream binary=new ByteArrayOutputStream();
	
	private CharArrayWriter charArrayWriter=new CharArrayWriter(512);
	
	public JMemoryResponseWrapper(HttpServletResponse response) {
		super(response);
	}
	
	@Override
	public PrintWriter getWriter() throws IOException {
		return new PrintWriter(charArrayWriter,true);
	}
	
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return new ServletOutputStream() {
			
			@Override
			public void write(int b) throws IOException {
				binary.write(b);
			}
			
			@Override
			public void setWriteListener(WriteListener writeListener) {
				
			}
			
			@Override
			public boolean isReady() {
				return true;
			}
		};
	}

	/**
	 * the method should be call after rendered . 
	 * @return
	 */
	public byte[] getBytes(){
		try{
			
			if(charArrayWriter.size()>0){
				return new String(charArrayWriter.toCharArray()).getBytes("utf-8");
			}
			if(binary.size()>0){
				return binary.toByteArray();
			}
			
		}catch(Exception e){
			LOGGER.error("Cannot get the actual HTML or Binary data.", e);
		}
		return null;
	}
	
}
