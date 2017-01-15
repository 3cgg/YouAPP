package me.bunny.kernel.jave.serializer;

import java.io.InputStream;
import java.io.OutputStream;

public interface JSerializer {

	/** Writes the bytes for the object to the output.
	 * <p>
	 * This method should not be called directly, 
	 **/
	abstract public void write (OutputStream output, Object object);

	/** Reads bytes and returns a new object of the specified concrete type.
	 * <p>
	 **/
	abstract public <T> T read (InputStream input, Class<T> type);
	
}
