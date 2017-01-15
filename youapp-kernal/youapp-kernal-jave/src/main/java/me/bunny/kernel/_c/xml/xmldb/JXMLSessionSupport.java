package me.bunny.kernel._c.xml.xmldb;

import me.bunny.kernel._c.proxy.JAtomicResourceSessionHolder;

public class JXMLSessionSupport {
	
	protected JXMLSession getXmlSession() throws Exception{
		JXMLSession session= (JXMLSession) JAtomicResourceSessionHolder.getAtomicResourceSession(true);
		if(session==null){
			session=new JXMLSession();
		}
		return session;
	}
	
}
