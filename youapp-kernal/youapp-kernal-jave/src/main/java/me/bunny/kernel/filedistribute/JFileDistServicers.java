/**
 * 
 */
package me.bunny.kernel.filedistribute;

/**
 * quickly to get file distribution service.
 * @author J
 */
public class JFileDistServicers {
	
	/**
	 * always return a single instance for caller.
	 * @param localRootDirectory
	 * @return JDefaultLocalFileDistService instance
	 */
	public static JFileDistService newSingleLocalFileDistService(String localRootDirectory){
		return JDefaultLocalFileDistService.newSingleLocalFileDistService(localRootDirectory);
	}
	
}
