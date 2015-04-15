package j.jave.framework.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * the prototype file. 
 * @author J
 *
 */
public class JFileResource extends JAbstractResource {

	private final File file;
	
	public JFileResource(File file) {
		this.file=file;
	}
	
	@Override
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(this.file);
	}
	
	@Override
	public long contentLength() throws IOException {
		return this.file.length();
	}
	
	@Override
	public boolean exists() {
		return this.file.exists();
	}
	
	@Override
	public File getFile() throws IOException {
		return this.file;
	}

	@Override
	public String getFilename() {
		return this.file.getName();
	}
	
	@Override
	public String getDescription() {
		return "file [" + this.file.getAbsolutePath() + "]";
	}
	
}
