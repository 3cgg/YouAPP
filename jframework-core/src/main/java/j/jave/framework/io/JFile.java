package j.jave.framework.io;

import j.jave.framework.utils.JFileUtils;
import j.jave.framework.utils.JUtils;

import java.io.File;
import java.io.IOException;


/**
 * transfering file in the network .  
 * Or copy the file to the target location.
 * @author J
 *
 */
public class JFile extends JFileResource{
	
	/**
	 * file name without extension . 
	 */
	private final String fileNameNoExtension;
	
	/**
	 * file extension , with dot(.)
	 */
	private final String fileExtension;
	
	/**
	 * the modified full file name user changed on the prototype one. 
	 */
	private final String expectedFullFileName;
	
	/**
	 * actual content with byte style. 
	 */
	private byte[] fileContent;
	
	public JFile(File file) {
		super(file);
		this.fileNameNoExtension=JFileUtils.getFileNameNoExtension(file);
		this.fileExtension=JFileUtils.getFileExtension(file);
		this.expectedFullFileName=getFilename();
	}
	
	public JFile(File file,String exptectedName) {
		super(file);
		this.fileExtension=JFileUtils.getFileExtension(file);
		fileNameNoExtension=getFilename();
		this.expectedFullFileName=exptectedName+"."+fileExtension;
	}

	public String getFileNameNoExtension() {
		return fileNameNoExtension;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public String getExpectedFullFileName() {
		return expectedFullFileName;
	}
	
	/**
	 * get actual content byte style. 
	 * @return the fileContent
	 */
	public byte[] getFileContent() {
		if(this.fileContent!=null) {
			return this.fileContent;
		}
			try {
				this.fileContent= JUtils.getBytes(getInputStream());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		return fileContent;
	}
	
	/**
	 * call to populate the actual byte[] from the inputstram . 
	 * <p>the file does not exist , but we already hold the bytes. 
	 * the case occurs when file is getting via HTTP.
	 */
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}
	
}