package j.jave.kernal.mail;

import javax.mail.Session;

public class JMailSession {

	private transient final Session session;
	
	/**
	 * the unique is calculated by those information that setup the {@link #session}
	 */
	private final String unique;
	
	public JMailSession(String unique,Session session) {
		this.unique=unique;
		this.session=session;
	}
	
	public Session getSession() {
		return session;
	}

	public String getUnique() {
		return unique;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.unique.equals(((JMailSession)obj).unique);
	}
	
	@Override
	public int hashCode() {
		return this.unique.hashCode();
	}
	
	
}
