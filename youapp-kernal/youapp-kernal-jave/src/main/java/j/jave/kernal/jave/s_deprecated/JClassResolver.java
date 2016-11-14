package j.jave.kernal.jave.s_deprecated;

import java.io.InputStream;
import java.io.OutputStream;

/** Handles class registration, writing class identifiers to bytes, and reading class identifiers from bytes.
 **/
public interface JClassResolver {
	/** Sets the JSO instance that this ClassResolver will be used for. This is called automatically by JSO. */
	public void setJSO (JSO jso);

	/** Stores the specified registration.
	 * @see Kryo#register(JRegistration) */
	public JRegistration register (JRegistration registration);

	/** Called when an unregistered type is encountered and {@link Kryo#setRegistrationRequired(boolean)} is false. */
	public JRegistration registerImplicit (Class type);

	/** Returns the registration for the specified class, or null if the class is not registered. */
	public JRegistration getRegistration (Class type);

	/** Writes a class and returns its registration.
	 * @param type May be null.
	 * @return Will be null if type is null. */
	public JRegistration writeClass (OutputStream output, Class type);

	/** Reads a class and returns its registration.
	 * @return May be null. */
	public JRegistration readClass (InputStream input);

	/** Called by {@link Kryo#reset()}. */
	public void reset ();
}
