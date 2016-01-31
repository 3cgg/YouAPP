package j.jave.kernal.mail;

import javax.mail.PasswordAuthentication;

/**
 * simple user name & password authenticator
 * @author J
 *
 */
public class JUserNamePasswordAuthenticator extends JMailAuthenticator {

	private String username;

	private String password;

	public JUserNamePasswordAuthenticator(String username, String password) {
		this.username = username;
		this.password = password;
	}

	String getPassword() {
		return password;
	}

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}

	String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String info() {
		return username+":"+password;
	}
	
}