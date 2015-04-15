/**
 * 
 */
package j.jave.framework.filedistribute;

import j.jave.framework.io.JFile;
import j.jave.framework.utils.JUtils;


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
		if(JUtils.isNullOrEmpty(expectedName)){
			expectedName=file.getFilename();
		}
		return JUtils.unique()+"-"+expectedName;
	}
	
	public String getFileExtension() {
		return file.getFileExtension();
	}
	
}
