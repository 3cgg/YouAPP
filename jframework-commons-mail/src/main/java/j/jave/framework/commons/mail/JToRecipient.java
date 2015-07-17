package j.jave.framework.commons.mail;

import javax.mail.Message.RecipientType;

public class JToRecipient extends JRecipient {
	
	public JToRecipient(String recipient) {
		super(recipient);
	}

	@Override
	public RecipientType getRecipientType() {
		return RecipientType.TO;
	}
	
}
