package j.jave.framework.commons.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * the prototype file. 
 * @author J
 *
 */
public class JFileResource extends JAbstractResource implements JOutputStreamSource {

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
	
	@Override
	public void write(byte[] bytes) {
		FileOutputStream fileOutputStream=null;
		try {
			fileOutputStream=new FileOutputStream(file);
			fileOutputStream.write(bytes);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e); 
			throw new RuntimeException(e);
		}finally{
			if(fileOutputStream!=null){
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e); 
					throw new RuntimeException(e);
				}
			}
		}
	}
	
}
