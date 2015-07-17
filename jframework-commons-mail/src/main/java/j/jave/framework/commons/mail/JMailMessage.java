package j.jave.framework.commons.mail;

import j.jave.framework.commons.model.JModel;

import java.util.HashSet;
import java.util.Set;

public class JMailMessage implements JModel {

	private String sender;
	
	private Set<JRecipient> recipients=new HashSet<JRecipient>();
	
	private String subject;
	
	private String content;
	
	/**
	 * like 'text/html;charset=utf-8'
	 */
	private String contentType="text/html;charset=utf-8";

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Set<JRecipient> getRecipients() {
		return recipients;
	}

	public void setRecipients(Set<JRecipient> recipients) {
		this.recipients = recipients;
	}
	
	public void addRecipient(JRecipient recipient){
		recipients.add(recipient);
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
