/**
 * 
 */
package j.jave.framework.commons.sqlloader;

/**
 * the uniform SQL configuration interface.
 * i.e. package name , jar name etc. 
 * @author J
 */
public interface JSQLConfigure {

	/**
	 * 
	 * @param packageName
	 */
	public void setPackageName(String packageName);
	
	/**
	 * like style . 
	 * @param jarName
	 */
	public void setJarName(String jarName);
	
}
