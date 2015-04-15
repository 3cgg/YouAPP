package j.jave.framework.io;

import java.io.File;
import java.io.IOException;

/**
 * resource interface . 
 * @author J
 *
 */
public interface JResource  extends JInputStreamSource{

	
	/**
	 * Return whether this resource actually exists in physical form.
	 * <p>This method performs a definitive existence check, whereas the
	 * existence of a {@code Resource} handle only guarantees a
	 * valid descriptor handle.
	 */
	boolean exists();
	
	
	/**
	 * Return whether this resource represents a handle with an open
	 * stream. If true, the InputStream cannot be read multiple times,
	 * and must be read and closed to avoid resource leaks.
	 * <p>Will be {@code false} for typical resource descriptors.
	 */
	boolean isOpen();
	
	
	
	/**
	 * Return a File handle for this resource.
	 * @throws IOException if the resource cannot be resolved as absolute
	 * file path, i.e. if the resource is not available in a file system
	 */
	File getFile() throws IOException;

	/**
	 * Determine the content length for this resource.
	 * @throws IOException if the resource cannot be resolved
	 * (in the file system or as some other known physical resource type)
	 */
	long contentLength() throws IOException;
	
	/**
	 * Determine a filename for this resource, i.e. typically the last
	 * part of the path: for example, "myfile.txt".
	 * <p>Returns {@code null} if this type of resource does not
	 * have a filename.
	 */
	String getFilename();

	/**
	 * Return a description for this resource,
	 * to be used for error output when working with the resource.
	 * <p>Implementations are also encouraged to return this value
	 * from their {@code toString} method.
	 * @see Object#toString()
	 */
	String getDescription();
	
}
