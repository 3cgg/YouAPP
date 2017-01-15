package me.bunny.modular._p.taskdriven.tkdd;

import javax.security.auth.Subject;

public class JTaskCustomConfig {

	private Subject subject;
	
	private JTaskMetadataSpecInit taskMetadataSpecInit;

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public JTaskMetadataSpecInit getTaskMetadataSpecInit() {
		return taskMetadataSpecInit;
	}

	public void setTaskMetadataSpecInit(JTaskMetadataSpecInit taskMetadataSpecInit) {
		this.taskMetadataSpecInit = taskMetadataSpecInit;
	}
	
}
