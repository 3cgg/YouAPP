/**
 * 
 */
package j.jave.kernal.jave.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * input stream wrapper.
 * @author J
 *
 */
public class JInputStreamWrapperSource extends JAbstractResource {

	private final InputStream inputStream;
	public JInputStreamWrapperSource(InputStream inputStream){
		this.inputStream=inputStream;
	}
	@Override
	public InputStream getInputStream() throws IOException {
		return inputStream;
	}

	@Override
	public String getDescription() {
		return "inputstream wrapper";
	}

}
