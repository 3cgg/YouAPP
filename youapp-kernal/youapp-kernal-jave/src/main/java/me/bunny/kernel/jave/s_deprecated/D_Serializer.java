package me.bunny.kernel.jave.s_deprecated;

import java.io.InputStream;
import java.io.OutputStream;


public abstract class D_Serializer<T>  {
	
	private boolean acceptsNull, immutable;

	/** Writes the bytes for the object to the output.
	 * <p>
	 * This method should not be called directly, 
	 **/
	abstract public void write (D_SO jso,OutputStream output, Object object);

	/** Reads bytes and returns a new object of the specified concrete type.
	 * <p>
	 **/
	abstract public T read (D_SO jso,InputStream input, Class<T> type);
	
	
	/** Returns a copy of the specified object. The default implementation returns the original if {@link #isImmutable()} is true,
	 * else throws {@link KryoException}. Subclasses should override this method if needed to support {@link Kryo#copy(Object)}.
	 * <p>
	 * Before Kryo can be used to copy child objects, {@link Kryo#reference(Object)} must be called with the copy to ensure it can
	 * be referenced by the child objects. Any serializer that uses {@link Kryo} to copy a child object may need to be reentrant.
	 * <p>
	 * This method should not be called directly, instead this serializer can be passed to {@link Kryo} copy methods that accept a
	 * serialier. */
	public T copy (D_SO jso, T original) {
		if (immutable) return original;
		throw new RuntimeException("Serializer does not support copy: " + getClass().getName());
	}
	
	
}
