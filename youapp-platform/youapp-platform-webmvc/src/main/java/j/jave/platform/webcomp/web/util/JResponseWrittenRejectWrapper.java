package j.jave.platform.webcomp.web.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import me.bunny.kernel._c.exception.JOperationNotSupportedException;

/**
 * 
 * <p>{@code CharArrayWriter } do with char response , such as JSP render.
 * <p>{@code ByteArrayOutputStream } do with binary response , such as JS,CSS etc.
 * @author J
 *
 */
public class JResponseWrittenRejectWrapper extends HttpServletResponseWrapper {
	
	public JResponseWrittenRejectWrapper(HttpServletResponse response) {
		super(response);
	}
	
	@Override
	public PrintWriter getWriter() throws IOException {
		throw new JOperationNotSupportedException("the response doesnot support write stream.");
	}
	
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		throw new JOperationNotSupportedException("the response doesnot support write stream.");
	}
	
}
