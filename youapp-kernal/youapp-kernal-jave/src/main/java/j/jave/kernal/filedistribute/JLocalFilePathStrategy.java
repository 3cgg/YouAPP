/**
 * 
 */
package j.jave.kernal.filedistribute;

import j.jave.kernal.jave.io.JFile;

import java.net.URI;

/**
 * resolve the URI from the file, it is extend for file distribution.
 * @author J
 */
public interface JLocalFilePathStrategy {
	
	URI resolveURI(JFile file);
	
}