package j.jave.kernal.mail;

import me.bunny.kernel._c.model.JModel;

public class JMail implements JModel {

	private JMailProtocol mailProtocol;
	
	private JMailAuthenticator mailAuthenticator;
	
	private JMailMessage mailMessage;

	public JMailProtocol getMailProtocol() {
		return mailProtocol;
	}

	public void setMailProtocol(JMailProtocol mailProtocol) {
		this.mailProtocol = mailProtocol;
	}

	public JMailAuthenticator getMailAuthenticator() {
		return mailAuthenticator;
	}

	public void setMailAuthenticator(JMailAuthenticator mailAuthenticator) {
		this.mailAuthenticator = mailAuthenticator;
	}

	public JMailMessage getMailMessage() {
		return mailMessage;
	}

	public void setMailMessage(JMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}
	
}
