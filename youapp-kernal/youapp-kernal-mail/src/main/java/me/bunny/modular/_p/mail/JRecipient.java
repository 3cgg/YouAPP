package me.bunny.modular._p.mail;

import javax.mail.Message.RecipientType;
import javax.mail.internet.MimeMessage;

public abstract class JRecipient {

	protected final String recipient;
	
	public JRecipient(String recipient) {
		this.recipient=recipient;
	}
	
	public abstract RecipientType getRecipientType();

	public String getRecipient() {
		return recipient;
	}
	
	public void addRecipient(MimeMessage message) throws Exception{
		message.addRecipients(getRecipientType(), recipient);
	}
	
}
