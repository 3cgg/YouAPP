/**
 * 
 */
package j.jave.framework.http;

import java.io.File;

import j.jave.framework.io.JFile;

/**
 * @author Administrator
 *
 */
public class JHttpFile extends JFile {

	/**
	 * @param file
	 */
	public JHttpFile(File file) {
		super(file);
	}
	
	public JHttpFile(File file,String exptectedName) {
		super(file, exptectedName);
	}

	/**
	 * attribute name for form . 
	 */
	private String attrName;
	
	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	
	
	
	
}
