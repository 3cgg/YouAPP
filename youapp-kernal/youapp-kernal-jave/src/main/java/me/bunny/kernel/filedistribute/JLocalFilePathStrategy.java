/**
 * 
 */
package me.bunny.kernel.filedistribute;

import java.net.URI;

import me.bunny.kernel.jave.io.JFile;

/**
 * resolve the URI from the file, it is extend for file distribution.
 * @author J
 */
public interface JLocalFilePathStrategy {
	
	URI resolveURI(JFile file);
	
}