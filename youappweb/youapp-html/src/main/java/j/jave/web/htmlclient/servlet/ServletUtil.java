package j.jave.web.htmlclient.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ServletUtil {

	public static final void writeBytesDirectly(HttpServletRequest request,
			HttpServletResponse response,byte[] bytes){
		OutputStream outputStream=null;
		try {
			outputStream=response.getOutputStream();
			response.getOutputStream().write(bytes);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(outputStream!=null){
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
