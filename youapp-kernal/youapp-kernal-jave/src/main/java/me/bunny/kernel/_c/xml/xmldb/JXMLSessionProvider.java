package me.bunny.kernel._c.xml.xmldb;

import me.bunny.kernel._c.proxy.JAtomicResourceSession;
import me.bunny.kernel._c.proxy.JAtomicResourceSessionProvider;

public class JXMLSessionProvider implements JAtomicResourceSessionProvider {

	@Override
	public JAtomicResourceSession newInstance() {
		return new JXMLSession();
	}
	
}
