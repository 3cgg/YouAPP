package me.bunny.kernel._c.s_deprecated;

import java.io.InputStream;
import java.io.OutputStream;

/** Handles class registration, writing class identifiers to bytes, and reading class identifiers from bytes.
 **/
public interface D_ClassResolver {
	/** Sets the JSO instance that this ClassResolver will be used for. This is called automatically by JSO. */
	public void setJSO (D_SO jso);

	/** Stores the specified registration.
	 * @see Kryo#register(D_Registration) */
	public D_Registration register (D_Registration registration);

	/** Called when an unregistered type is encountered and {@link Kryo#setRegistrationRequired(boolean)} is false. */
	public D_Registration registerImplicit (Class type);

	/** Returns the registration for the specified class, or null if the class is not registered. */
	public D_Registration getRegistration (Class type);

	/** Writes a class and returns its registration.
	 * @param type May be null.
	 * @return Will be null if type is null. */
	public D_Registration writeClass (OutputStream output, Class type);

	/** Reads a class and returns its registration.
	 * @return May be null. */
	public D_Registration readClass (InputStream input);

	/** Called by {@link Kryo#reset()}. */
	public void reset ();
}
