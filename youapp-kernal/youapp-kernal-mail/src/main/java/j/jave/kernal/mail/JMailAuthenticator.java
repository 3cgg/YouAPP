package j.jave.kernal.mail;

import javax.mail.Authenticator;

public abstract class JMailAuthenticator extends  Authenticator {

	/**
	 * if the info is the same as each other, consider the object is the same.
	 * @return
	 * @see #hashCode()
	 * @see #equals(Object)
	 */
	public abstract String info();
	
	@Override
	public final int hashCode() {
		return info().hashCode();
	}
	
	@Override
	public final boolean equals(Object obj) {
		return info().equals(((JMailAuthenticator)obj).info());
	}
	
}
