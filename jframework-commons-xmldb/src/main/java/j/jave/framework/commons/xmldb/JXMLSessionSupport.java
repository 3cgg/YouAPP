package j.jave.framework.commons.xmldb;

import j.jave.framework.commons.proxy.JAtomicResourceSessionHolder;

public class JXMLSessionSupport {
	
	protected JXMLSession getXmlSession() throws Exception{
		JXMLSession session= (JXMLSession) JAtomicResourceSessionHolder.getAtomicResourceSession(true);
		if(session==null){
			session=new JXMLSession();
		}
		return session;
	}
	
}
