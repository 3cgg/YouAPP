package me.bunny.kernel.jave.support._resource;


/**
 * file relative path filter, used in scanning files under a parent directory.
 * @author J
 */
public interface JFilePathFilterConfig extends JFileNameFilterConfig{

	/**
	 * set relative path. like "j/jave/framework/". the last slash is mandatory.
	 * @param path
	 */
	void setRelativePath(String... paths);
	
	
	
}
