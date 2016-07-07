package j.jave.kernal.jave.aop;

import j.jave.kernal.jave.model.JModel;

public class JProxyConfig implements JModel{

	private JTargetSource targetSource;

	public JTargetSource getTargetSource() {
		return targetSource;
	}

	public void setTargetSource(JTargetSource targetSource) {
		this.targetSource = targetSource;
	}
	
}
