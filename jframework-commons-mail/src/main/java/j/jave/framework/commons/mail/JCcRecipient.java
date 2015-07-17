package j.jave.framework.commons.mail;

import javax.mail.Message.RecipientType;

public class JCcRecipient extends JRecipient {
	
	public JCcRecipient(String recipient) {
		super(recipient);
	}

	@Override
	public RecipientType getRecipientType() {
		return RecipientType.CC;
	}
	
}
