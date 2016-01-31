/**
 * 
 */
package j.jave.kernal.filedistribute;

import j.jave.kernal.jave.io.JFile;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.utils.JUniqueUtils;


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
