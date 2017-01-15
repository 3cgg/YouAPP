package j.jave.kernal.streaming.kafka;

import me.bunny.kernel._c.model.JModel;

public class Notify implements JModel{

	private String notifier;
	
	private String content;
	
	private String uri;

	public String getNotifier() {
		return notifier;
	}

	public void setNotifier(String notifier) {
		this.notifier = notifier;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}
