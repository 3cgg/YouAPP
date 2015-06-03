
package j.jave.framework.base64;

import j.jave.framework.extension.base64.JBase64;
import j.jave.framework.servicehub.JServiceFactorySupport;

public class JBase64ServiceFactory extends JServiceFactorySupport<JBase64> {

	public JBase64ServiceFactory(){
		super(JBase64.class);
	}
	
	public JBase64ServiceFactory(Class<JBase64> registClass) {
		super(registClass);
	}

	@Override
	public JBase64 getService() {
		return JBase64Factory.getBase64Factory().getBase64();
	}

}
