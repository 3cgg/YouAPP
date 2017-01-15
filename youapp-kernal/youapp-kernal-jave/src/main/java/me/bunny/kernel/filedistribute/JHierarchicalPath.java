/**
 * 
 */
package me.bunny.kernel.filedistribute;

import me.bunny.kernel._c.io.JFile;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.kernel._c.utils.JUniqueUtils;


/**
 * 
 * @author J
 */
public class JHierarchicalPath {
	
	private final JFile file;

	public JHierarchicalPath(JFile file) {
		this.file=file;
	}
	
	private String root;

	/**
	 * @return the root
	 */
	public String getRoot() {
		return root;
	}
	
	public void setRoot(String root) {
		this.root = root;
	}
	
	public String uniqueName(){
		String expectedName=file.getExpectedFullFileName();
		if(JStringUtils.isNullOrEmpty(expectedName)){
			expectedName=file.getFilename();
		}
		return JUniqueUtils.unique()+"-"+expectedName;
	}
	
	public String getFileExtension() {
		return "."+file.getFileExtension();
	}
	
}
