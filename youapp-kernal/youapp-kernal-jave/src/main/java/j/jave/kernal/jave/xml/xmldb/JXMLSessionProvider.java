package j.jave.kernal.jave.xml.xmldb;

import j.jave.kernal.jave.proxy.JAtomicResourceSession;
import j.jave.kernal.jave.proxy.JAtomicResourceSessionProvider;

public class JXMLSessionProvider implements JAtomicResourceSessionProvider {

	@Override
	public JAtomicResourceSession newInstance() {
		return new JXMLSession();
	}
	
}
