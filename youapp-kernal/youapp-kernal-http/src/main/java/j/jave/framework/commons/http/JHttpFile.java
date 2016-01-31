/**
 * 
 */
package j.jave.framework.commons.http;

import j.jave.framework.commons.io.JFile;

import java.io.File;

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
