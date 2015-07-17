package j.jave.framework.commons.xmldb;

import j.jave.framework.commons.proxy.JAtomicResourceSession;
import j.jave.framework.commons.proxy.JAtomicResourceSessionProvide;

public class JXMLSessionProvider implements JAtomicResourceSessionProvide {

	@Override
	public JAtomicResourceSession newInstance() {
		return new JXMLSession();
	}
	
}
