package j.jave.kernal.xmldb;

import j.jave.kernal.jave.proxy.JAtomicResourceSession;
import j.jave.kernal.jave.proxy.JAtomicResourceSessionProvide;

public class JXMLSessionProvider implements JAtomicResourceSessionProvide {

	@Override
	public JAtomicResourceSession newInstance() {
		return new JXMLSession();
	}
	
}
