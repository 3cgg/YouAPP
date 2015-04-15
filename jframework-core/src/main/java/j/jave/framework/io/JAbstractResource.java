package j.jave.framework.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.util.Assert;

public abstract class JAbstractResource implements JResource {

	@Override
	public boolean exists() {
		try {
			return getFile().exists();
		}
		catch (IOException ex) {
			// Fall back to stream existence: can we open the stream?
			try {
				InputStream is = getInputStream();
				is.close();
				return true;
			}
			catch (Throwable isEx) {
				return false;
			}
		}
	}

	@Override
	public boolean isOpen() {
		return true;
	}

	@Override
	public File getFile() throws IOException {
		return null;
	}

	@Override
	public long contentLength() throws IOException {
		InputStream is = this.getInputStream();
		Assert.state(is != null, "resource input stream must not be null");
		try {
			long size = 0;
			byte[] buf = new byte[255];
			int read;
			while ((read = is.read(buf)) != -1) {
				size += read;
			}
			return size;
		}
		finally {
			try {
				is.close();
			}
			catch (IOException ex) {
			}
		}
	}

	@Override
	public String getFilename() {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}

}
