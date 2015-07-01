/**
 * 
 */
package j.jave.framework.commons.filedistribute;

import j.jave.framework.commons.io.JFile;

import java.net.URI;

/**
 * resolve the URI from the file, it is extend for file distribution.
 * @author J
 */
public interface JLocalFilePathStrategy {
	
	URI resolveURI(JFile file);
	
}