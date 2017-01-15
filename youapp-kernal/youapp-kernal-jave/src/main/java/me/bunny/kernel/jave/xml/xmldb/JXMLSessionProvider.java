package me.bunny.kernel.jave.xml.xmldb;

import me.bunny.kernel.jave.proxy.JAtomicResourceSession;
import me.bunny.kernel.jave.proxy.JAtomicResourceSessionProvider;

public class JXMLSessionProvider implements JAtomicResourceSessionProvider {

	@Override
	public JAtomicResourceSession newInstance() {
		return new JXMLSession();
	}
	
}
