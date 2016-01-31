package j.jave.kernal.mail;

import java.util.Properties;

public class JSMTPProtocol implements JMailProtocol {

	private final boolean authorized;
	
	private final String smtp;
	
	public JSMTPProtocol(String smtp,boolean authorized) {
		this.smtp=smtp;
		this.authorized=authorized;
	}
	
	
	@Override
	public void set(Properties properties) {
		properties.put("mail.smtp.auth", authorized);
		properties.put("mail.smtp.host", smtp);
	}

}
